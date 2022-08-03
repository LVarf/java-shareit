package ru.practicum.shareit.user.dto;

import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class UserDTO {
    private long id;
    @NotBlank
    private String name;
    @Email
    private String email;
    private List<Item> userItems;

    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.userItems = user.getUserItems();
    }
}
