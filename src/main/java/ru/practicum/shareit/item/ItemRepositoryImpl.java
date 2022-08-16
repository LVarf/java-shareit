package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.*;

/*
public class ItemRepositoryImpl implements ItemRepository{
    private final Map<Long, List<Item>> items = new HashMap<>();
    private long newItemId = 1;

    @Override
    public Map<Long, List<Item>> getAllItems() {
        return items;
    }

    @Override
    public List<Item> findByUserId(long userId) {
        return items.getOrDefault(userId, Collections.emptyList());
    }

    @Override
    public Item save(Item item) {
        item.setId(getId());
        items.compute(item.getOwner().getId(), (owner, userItems) -> {
                    if (userItems == null)
                        userItems = new ArrayList<>();
                    userItems.add(item);
                    return userItems;
                }
        );
        return item;
    }

    @Override
    public void deleteByUserIdAndByItemId(long userId, long itemId) {
        if(items.containsKey(userId)) {
            List<Item> userItems = items.get(userId);
            userItems.removeIf(item -> item.getId() == itemId);
        }
    }

    private long getId() {
        return newItemId++;
    }
}*/
