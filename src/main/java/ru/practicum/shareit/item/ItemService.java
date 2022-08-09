package ru.practicum.shareit.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.practicum.shareit.item.dto.ItemDTO;

import java.util.List;

public interface ItemService {

    ItemDTO postItem(ItemDTO item, long userId) throws Exception;

    ItemDTO updateItem(long itemId, long userId, ItemDTO itemDTO);

    ItemDTO getItemByItemId(long itemId);

    List<ItemDTO> getItemsByUserId(long userId);

    List<ItemDTO> searchItems(String text);
}
