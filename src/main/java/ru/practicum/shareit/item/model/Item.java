package ru.practicum.shareit.item.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.requests.ItemRequest;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * // TODO .
 */
@Entity
@Data
@Builder(toBuilder = true)
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue
    private long id;
    @NotBlank
    @NotEmpty
    @Column
    private String name;
    @NotBlank
    @NotEmpty
    @Column
    private String description;
    @NotNull
    @Column
    private Boolean available;
    @Column
    private Long owner;
    @Column
    private Long request;
}
