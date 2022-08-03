package ru.practicum.shareit.item;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundObjectException;
import ru.practicum.shareit.item.dto.ItemDTO;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;
    private final UserService userService;

    @Override
    public ItemDTO postItem(Item item, long userId){
        userService.getUserById(userId);
        item.setOwner(userId);
        return new ItemDTO(itemRepository.save(item));
    }

    //region updateItem
    @Override
    public ItemDTO updateItem(long itemId, long userId, String params) throws JsonProcessingException {
        userService.getUserById(userId);
        if(!itemRepository.getAllItems().containsKey(userId))
            throw new NotFoundObjectException("The user has no any items");
        if(!itemRepository.getAllItems()
                .get(userId)
                .stream()
                .anyMatch(i -> i.getId() == itemId))
            throw new NotFoundObjectException("The user has no the item");


        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(params);

        Item item = itemRepository.findByUserId(userId)
                .stream()
                .filter(s -> s.getId() == itemId)
                .collect(Collectors.toList())
                .get(0);

        if(jsonNode.has("name"))
            item.setName(jsonNode.get("name").asText());
        if(jsonNode.has("description"))
            item.setDescription(jsonNode.get("description").asText());
        if(jsonNode.has("available"))
            item.setAvailable(jsonNode.get("available").asBoolean());

        return new ItemDTO(item);
    }
    //endregion

    //region getItemByItemId
    @Override
    public ItemDTO getItemByItemId(long itemId) {

        if(!itemRepository.getAllItems()
                .values().stream()
                .flatMap(List::stream)
                .anyMatch(item -> item.getId() == itemId))
            throw new NotFoundObjectException("Has no item");

        return new ItemDTO(itemRepository.getAllItems()
                .values().stream()
                .flatMap(List::stream)
                .filter(item -> item.getId() == itemId)
                .collect(Collectors.toList()).get(0));
    }
    //endregion

    //region getItemByUserId
    @Override
    public List<ItemDTO> getItemsByUserId(long userId) {
        userService.getUserById(userId);
        if(!itemRepository.getAllItems().containsKey(userId))
            throw new NotFoundObjectException("The user has no items");

        return itemRepository.getAllItems().get(userId)
                .stream()
                .map(ItemDTO::new)
                .collect(Collectors.toList());
    }
    //endregion

    @Override
    public List<ItemDTO> searchItems(String text) {
        if(text.isEmpty())
            return List.of();
        return itemRepository.getAllItems().values()
                .stream()
                .flatMap(List::stream)
                .filter(Item::getAvailable)
                .filter(item -> item.getName().toLowerCase().contains(text.toLowerCase())
                        || item.getDescription().toLowerCase().contains(text.toLowerCase()))
                .map(ItemDTO::new)
                .collect(Collectors.toList());
    }
}
