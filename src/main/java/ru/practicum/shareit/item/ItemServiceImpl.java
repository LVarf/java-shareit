package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingDTO;
import ru.practicum.shareit.booking.mapping.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exceptions.NotFoundObjectException;
import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.item.dto.CommentDTO;
import ru.practicum.shareit.item.dto.ItemDTO;
import ru.practicum.shareit.item.dto.ItemDTOForGetByItemId;
import ru.practicum.shareit.item.mapping.CommentMapper;
import ru.practicum.shareit.item.mapping.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;


    //region postItem
    @Transactional
    @Override
    public ItemDTO postItem(ItemDTO itemDTO, Long userId){
        itemDTO.setOwner(userService.getUserById(userId));
        itemDTO = ItemMapper.mapperToItemDTO(itemRepository.save(ItemMapper.mapperToItem(itemDTO, userService)));
        return itemDTO;
    }
    //endregion

    //region updateItem
    @Transactional
    @Override
    public ItemDTO updateItem(Long itemId, Long userId, ItemDTO itemDTO){
       if(itemRepository.findById(itemId).isPresent()) {
           if(itemRepository.findById(itemId).get().getOwner().getId() != userId)
               throw new NotFoundObjectException("The user has no this item");
       } else throw new NotFoundObjectException("There is no items");

       Item item = itemRepository.findById(itemId).get();

        if(itemDTO.getName() != null)
            item.setName(itemDTO.getName());
        if(itemDTO.getDescription() != null)
            item.setDescription(itemDTO.getDescription());
        if(itemDTO.getAvailable() != null)
            item.setAvailable(itemDTO.getAvailable());

        itemRepository.save(item);

        return ItemMapper.mapperToItemDTO(item);
    }
    //endregion

    //region getItemByItemId
    @Transactional(readOnly = true)
    @Override
    public ItemDTOForGetByItemId getItemByItemId(Long itemId, Long userId) {

        if(itemRepository.findById(itemId).isEmpty())
            throw new NotFoundObjectException("Has no item");

        ItemDTOForGetByItemId itemDTO = ItemMapper.mapperToItemDTOForGetByItemId(itemRepository.findById(itemId).get());
        if (userId == itemDTO.getOwner().getId()) {
            itemDTO.setNextBooking(functionNext.apply(itemId));
            itemDTO.setLastBooking(functionLast.apply(itemId));
        }
        itemDTO.setComments(commentRepository
                .findAllByItemId(itemId)
                .get()
                .stream()
                .map(CommentMapper::mapperToCommentDTO)
                .collect(Collectors.toList())
        );
        return itemDTO;
    }
    //endregion


    //region getItemByUserId
    @Transactional(readOnly = true)
    @Override
    public List<ItemDTOForGetByItemId> getItemsByUserId(Long userId) {

        return itemRepository.findByOwner(userId)
                .stream()
                .map((ItemMapper::mapperToItemDTOForGetByItemId))
                .peek(i -> i.setLastBooking(functionLast.apply(i.getId())))
                .peek(i -> i.setNextBooking(functionNext.apply(i.getId())))
                .collect(Collectors.toList());
    }
    //endregion

    //region search items by text
    @Transactional(readOnly = true)
    @Override
    public List<ItemDTO> searchItems(String text) {
        if(text.isEmpty())
            return List.of();
        return itemRepository.findByContainsText(text.toLowerCase())
                .stream()
                .map(ItemMapper::mapperToItemDTO)
                .collect(Collectors.toList());
                /*.stream()
                .filter(Item::getAvailable)
                .filter(item -> item.getName().toLowerCase().contains(text.toLowerCase())
                        || item.getDescription().toLowerCase().contains(text.toLowerCase()))
                .map(ItemMapper::mapperToItemDTO)
                .collect(Collectors.toList());*/
    }
    //endregion

    Function<Long, BookingDTO> functionLast = new Function<Long, BookingDTO>() {
        @Override
        public BookingDTO apply(Long itemId) {
            List<Booking> list = bookingRepository.findLastBooking(itemId, LocalDateTime.now()).get();
            if (list.isEmpty()) {
                return null;
            } else
                return BookingMapper.mapperToBookingDTO(list.get(0));
        }
    };
    Function<Long, BookingDTO> functionNext = new Function<Long, BookingDTO>() {
        @Override
        public BookingDTO apply(Long itemId) {
            List<Booking> list = bookingRepository.findNextBooking(itemId, LocalDateTime.now()).get();
            if (list.isEmpty()) {
                return null;
            } else
                return BookingMapper.mapperToBookingDTO(list.get(0));
        }
    };

    @Override
    public CommentDTO postComment(CommentDTO commentDTO, Long userId, Long itemId) {
        /**
         * запрос в BookingRepository
         * проверка:
         * - бронирование пользователем существует;
         * - статус бронирования approved;
         * - аренда завершена.
         */

        List<Booking> list = bookingRepository
                .findBookingForCreateComment(itemId, userId, LocalDateTime.now())
                .get();

        if (list.isEmpty())
            throw new BadRequestException("bad request");

        Booking booking = list.get(0);

        Comment comment = new Comment();
        comment.setText(commentDTO.getText());
        comment.setItem(booking.getItem());
        comment.setAuthor(booking.getBooker());
        comment.setCreated(commentDTO.getCreated());
        return CommentMapper.mapperToCommentDTO(
                commentRepository.save(comment)
        );
    }
}
