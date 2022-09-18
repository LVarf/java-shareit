package ru.practicum.shareit.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice("ru.practicum.shareit")
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)//400
    public Map<String, String> badRequest(final ValidationException e) {
        return Map.of(
                "Error message", e.getMessage()
        );
    }
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)//400
    public Map<String, String> handleUnsupportedStatusException(BadRequestException e) {
        Map<String, String> map = new HashMap<>();
        map.put("error", e.getMessage());
        return map;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)//404
    public Map<String, String> handleBadRequestException(NotFoundRequestException e) {
        Map<String, String> map = new HashMap<>();
        map.put("error", "Not found : " + e.getMessage());
        return map;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)//404
    public ErrorResponse notFound(final NotFoundObjectException e) {
        return new ErrorResponse("Bad request", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)//500
    public Map<String, String> handleException (final RuntimeException e) {
        return Map.of(
                "Error message", "RuntimeException: " + e.getMessage()
        );
    }

}

@RequiredArgsConstructor
@Getter
class ErrorResponse{
    private final String error;
    private final String description;
}
