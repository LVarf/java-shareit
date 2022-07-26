package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDTO;
import ru.practicum.shareit.item.dto.ItemDTO;
import ru.practicum.shareit.item.dto.ItemDTOForGetByItemId;

import javax.validation.Valid;
import java.util.List;

/**
 * // TODO .
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDTO postItem(@RequestBody @Valid ItemDTO item,
                            @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.postItem(item, userId);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDTO postComment(@RequestBody @Valid CommentDTO comment,
                               @RequestHeader("X-Sharer-User-Id") long userId,
                               @PathVariable Long itemId) {
        return itemService.postComment(comment, userId, itemId);
    }

    @PatchMapping("/{itemId}")
    public ItemDTO updateItem(@PathVariable long itemId,
                              @RequestHeader("X-Sharer-User-Id") long userId,
                              @RequestBody ItemDTO itemDTO) {
        return itemService.updateItem(itemId, userId, itemDTO);
    }

    @GetMapping("/{itemId}")
    public ItemDTOForGetByItemId getItemById(@PathVariable long itemId,
                                             @RequestHeader(value = "X-Sharer-User-Id", required = false) long userId) {
        return itemService.getItemByItemId(itemId, userId);
    }

    @GetMapping
    public List<ItemDTOForGetByItemId> getItemsByUserID(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.getItemsByUserId(userId);
    }

    @GetMapping("/search")
    public List<ItemDTO> searchItems(@RequestParam("text") String text) {
        return itemService.searchItems(text);
    }
}
