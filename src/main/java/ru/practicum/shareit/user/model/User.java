package ru.practicum.shareit.user.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.item.model.Item;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * // TODO .
 */
@Data
public class User {
    public long id;
    @NotBlank
    private String name;
    @Email
    @NotEmpty
    private String email;
    @EqualsAndHashCode.Exclude
    private List<Item> userItems;
}
