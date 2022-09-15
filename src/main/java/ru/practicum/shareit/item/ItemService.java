package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.CommentDTO;
import ru.practicum.shareit.item.dto.ItemDTO;
import ru.practicum.shareit.item.dto.ItemDTOForGetByItemId;

import java.util.List;

public interface ItemService {

    ItemDTO postItem(ItemDTO item, Long userId);
    CommentDTO postComment(CommentDTO commentDTO,
                           Long userId,
                           Long itemId);

    ItemDTO updateItem(Long itemId, Long userId, ItemDTO itemDTO);

    ItemDTOForGetByItemId getItemByItemId(Long itemId, Long userId);

    List<ItemDTOForGetByItemId> getItemsByUserId(Long userId);

    List<ItemDTO> searchItems(String text);
}
