package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundObjectException;
import ru.practicum.shareit.item.dto.ItemDTO;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;
    private final UserService userService;

    //region postItem
    @Override
    public ItemDTO postItem(ItemDTO item, long userId){
        userService.getUserById(userId);
        item.setOwner(userId);
        itemRepository.save(ItemMapper.mapperToItem(item));
        return ItemMapper.mapperToItemDTO(itemRepository.save(ItemMapper.mapperToItem(item)));
    }
    //endregion

    //region updateItem
    @Override
    public ItemDTO updateItem(long itemId, long userId, ItemDTO itemDTO){
       if(itemRepository.findById(itemId).isPresent()) {
           if(itemRepository.findById(itemId).get().getOwner() != userId)
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
    @Override
    public ItemDTO getItemByItemId(long itemId) {

        if(itemRepository.findById(itemId).isEmpty())
            throw new NotFoundObjectException("Has no item");

        return ItemMapper.mapperToItemDTO(itemRepository.findById(itemId).get());
    }
    //endregion

    //region getItemByUserId
    @Override
    public List<ItemDTO> getItemsByUserId(long userId) {
        userService.getUserById(userId);
        if(itemRepository.findByOwner(userId).isEmpty())
            throw new NotFoundObjectException("The user has no items");

        return itemRepository.findByOwner(userId)
                .stream()
                .flatMap(List::stream)
                .map(ItemMapper::mapperToItemDTO)
                .collect(Collectors.toList());
    }
    //endregion

    //region search items by text
    @Override
    public List<ItemDTO> searchItems(String text) {
        if(text.isEmpty())
            return List.of();
        //return itemRepository.getAllItems().values()
        return itemRepository.findAll()
                .stream()
                .filter(Item::getAvailable)
                .filter(item -> item.getName().toLowerCase().contains(text.toLowerCase())
                        || item.getDescription().toLowerCase().contains(text.toLowerCase()))
                .map(ItemMapper::mapperToItemDTO)
                .collect(Collectors.toList());
    }
    //endregion
}
