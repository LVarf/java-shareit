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
        return ItemMapper.mapperToItemDTO(itemRepository.save(ItemMapper.mapperToItem(item, userService)));
    }
    //endregion

    //region updateItem
    @Override
    public ItemDTO updateItem(long itemId, long userId, ItemDTO itemDTO){
        userService.getUserById(userId);
        if(!itemRepository.getAllItems().containsKey(userId))
            throw new NotFoundObjectException("The user has no any items");
        if(itemRepository.getAllItems()
                .get(userId)
                .stream()
                .noneMatch(i -> i.getId() == itemId))
            throw new NotFoundObjectException("The user has no the item");

        Item item = itemRepository.findByUserId(userId)
                .stream()
                .filter(s -> s.getId() == itemId)
                .collect(Collectors.toList())
                .get(0);

        if(itemDTO.getName() != null)
            item.setName(itemDTO.getName());
        if(itemDTO.getDescription() != null)
            item.setDescription(itemDTO.getDescription());
        if(itemDTO.getAvailable() != null)
            item.setAvailable(itemDTO.getAvailable());

        return ItemMapper.mapperToItemDTO(item);
    }
    //endregion

    //region getItemByItemId
    @Override
    public ItemDTO getItemByItemId(long itemId) {

        if(!itemRepository.getAllItems()
                .values().stream()
                .flatMap(List::stream)
                .anyMatch(item -> item.getId() == itemId))
            throw new NotFoundObjectException("Has no item");

        return ItemMapper.mapperToItemDTO(itemRepository.getAllItems()
                .values().stream()
                .flatMap(List::stream)
                .filter(item -> item.getId() == itemId)
                .collect(Collectors.toList()).get(0));
    }
    //endregion

    //region getItemByUserId
    @Override
    public List<ItemDTO> getItemsByUserId(long userId) {
        userService.getUserById(userId);
        if(!itemRepository.getAllItems().containsKey(userId))
            throw new NotFoundObjectException("The user has no items");

        return itemRepository.getAllItems().get(userId)
                .stream()
                .map(ItemMapper::mapperToItemDTO)
                .collect(Collectors.toList());
    }
    //endregion

    //region search items by text
    @Override
    public List<ItemDTO> searchItems(String text) {
        if(text.isEmpty())
            return List.of();
        return itemRepository.getAllItems().values()
                .stream()
                .flatMap(List::stream)
                .filter(Item::getAvailable)
                .filter(item -> item.getName().toLowerCase().contains(text.toLowerCase())
                        || item.getDescription().toLowerCase().contains(text.toLowerCase()))
                .map(ItemMapper::mapperToItemDTO)
                .collect(Collectors.toList());
    }
    //endregion
}
