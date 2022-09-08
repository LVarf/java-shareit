package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDTO;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserService;

public class ItemMapper {

    public static ItemDTO mapperToItemDTO (Item item) {
        return ItemDTO.builder()
                .available(item.getAvailable())
                .owner(item.getOwner().getId())
                .id(item.getId())
                .description(item.getDescription())
                .name(item.getName())
                .build();
    }

    public static Item mapperToItem (ItemDTO itemDTO, UserService userService) {
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setAvailable(itemDTO.getAvailable());
        item.setOwner(UserMapper.mapperToUser(userService.getUserById(itemDTO.getOwner())));
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        return item;
    }
}
