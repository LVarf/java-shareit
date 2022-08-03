package ru.practicum.shareit.requests;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * // TODO .
 */
@Data
public class ItemRequest {
    private final long id;
    private final String description;
    private final long requester;
    private final LocalDateTime created;
}
