package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exceptions.NotFoundObjectException;
import ru.practicum.shareit.item.dto.ItemDTO;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;
    private final UserService userService;


    //region postItem
    @Transactional
    @Override
    public ItemDTO postItem(ItemDTO itemDTO, Long userId){
        userService.getUserById(userId);
        itemDTO.setOwner(userId);
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
    public ItemDTO getItemByItemId(Long itemId) {

        if(itemRepository.findById(itemId).isEmpty())
            throw new NotFoundObjectException("Has no item");

        return ItemMapper.mapperToItemDTO(itemRepository.findById(itemId).get());
    }
    //endregion

    //region getItemByUserId
    @Transactional(readOnly = true)
    @Override
    public List<ItemDTO> getItemsByUserId(Long userId) {
        List<ItemDTO> items = itemRepository.findByOwner(userId)
                .stream()
                .map((ItemMapper::mapperToItemDTO))
                .collect(Collectors.toList());

        return items;
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
}
