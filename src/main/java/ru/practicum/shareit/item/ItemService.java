package ru.practicum.shareit.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.practicum.shareit.item.dto.ItemDTO;

import java.util.List;

public interface ItemService {

    ItemDTO postItem(ItemDTO item, Long userId);

    ItemDTO updateItem(Long itemId, Long userId, ItemDTO itemDTO);

    ItemDTO getItemByItemId(Long itemId);

    List<ItemDTO> getItemsByUserId(Long userId);

    List<ItemDTO> searchItems(String text);
}
