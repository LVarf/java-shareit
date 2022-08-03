package ru.practicum.shareit.item.model;

import lombok.Data;
import lombok.NonNull;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * // TODO .
 */
@Data
public class Item {
    private long id;
    @NotBlank
    @NotEmpty
    private String name;
    @NotBlank
    @NotEmpty
    private String description;
    @NotNull
    private Boolean available;
    private long owner;
    private long request;
}
