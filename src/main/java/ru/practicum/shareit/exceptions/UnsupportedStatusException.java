package ru.practicum.shareit.exceptions;

public class UnsupportedStatusException extends IllegalArgumentException {

    public UnsupportedStatusException(String message) {
        super(message);
    }
}
