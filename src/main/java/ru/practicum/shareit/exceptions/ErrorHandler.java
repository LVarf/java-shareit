package ru.practicum.shareit.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice("ru.practicum.shareit")
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)//400
    public ErrorResponse badRequest(final ValidationException e) {
        return new ErrorResponse("Ошибка запроса", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)//404
    public ErrorResponse notFound(final NotFoundObjectException e) {
        return new ErrorResponse("Ошибка запроса", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)//500
    public Map<String, String> handleException ( final RuntimeException e) {
        return Map.of(
                "error", "Произошла ошибка!",
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
