package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Map;

public interface ItemRepository {

    Map<Long, List<Item>> getAllItems();

    List<Item> findByUserId(long userId);

    Item save(Item item);

    void deleteByUserIdAndByItemId(long userId, long itemId);
}
