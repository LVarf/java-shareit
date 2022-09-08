package ru.practicum.shareit.item.model;

import lombok.Data;
import ru.practicum.shareit.requests.ItemRequest;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * // TODO .
 */
@Entity
@Data
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;
    @NotNull
    @NotBlank
    @Column(name = "description", nullable = false)
    private String description;
    /*@NotNull
    @NotBlank*/
    @Column(name = "is_available", nullable = false)
    private Boolean available = false;
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;
    @ManyToOne(fetch = FetchType.LAZY)
    private ItemRequest request;
}
