package ru.practicum.shareit.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.practicum.shareit.item.dto.ItemDTO;
import ru.practicum.shareit.item.model.Item;

import javax.management.BadAttributeValueExpException;
import java.util.List;

public interface ItemService {

    ItemDTO postItem(Item item, long userId) throws Exception;

    ItemDTO updateItem(long itemId, long userId, String param) throws JsonProcessingException;

    ItemDTO getItemByItemId(long itemId);

    List<ItemDTO> getItemsByUserId(long userId);

    List<ItemDTO> searchItems(String text);
}
