package ru.practicum.shareit.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.practicum.shareit.item.dto.ItemDTO;
import ru.practicum.shareit.item.dto.ItemDTOForGetByItemId;

import java.util.List;

public interface ItemService {

    ItemDTO postItem(ItemDTO item, Long userId);

    ItemDTO updateItem(Long itemId, Long userId, ItemDTO itemDTO);

    ItemDTOForGetByItemId getItemByItemId(Long itemId, Long userId);

    List<ItemDTOForGetByItemId> getItemsByUserId(Long userId);

    List<ItemDTO> searchItems(String text);
}
