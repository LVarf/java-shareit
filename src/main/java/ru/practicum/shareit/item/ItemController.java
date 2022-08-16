package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDTO;

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
                            @RequestHeader("X-Sharer-User-Id") long userId) throws Exception {
        return itemService.postItem(item, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDTO updateItem(@PathVariable long itemId,
                              @RequestHeader("X-Sharer-User-Id") long userId,
                              @RequestBody ItemDTO itemDTO) {
        return itemService.updateItem(itemId, userId, itemDTO);
    }

    @GetMapping("/{itemId}")
    public ItemDTO getItemById(@PathVariable long itemId) {
        return itemService.getItemByItemId(itemId);
    }

    @GetMapping
    public List<ItemDTO> getItemsByUserID(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.getItemsByUserId(userId);
    }

    @GetMapping("/search")
    public List<ItemDTO> searchItems(@RequestParam("text") String text) {
        return itemService.searchItems(text);
    }
}
