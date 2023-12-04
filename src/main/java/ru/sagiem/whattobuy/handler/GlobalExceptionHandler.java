package ru.sagiem.whattobuy.handler;

import static ru.sagiem.whattobuy.utils.ResponseUtils.ENTITY_NOT_FOUND_EXCEPTION_MESSAGE;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.sagiem.whattobuy.dto.ExceptionResponse;
import ru.sagiem.whattobuy.exception.ShoppingProjectNotFoundException;
import ru.sagiem.whattobuy.utils.ResponseUtils;


@Component
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ShoppingProjectNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(ShoppingProjectNotFoundException exception) {
        ExceptionResponse response = ResponseUtils.getExceptionResponse(
                HttpStatus.NOT_FOUND,
                ENTITY_NOT_FOUND_EXCEPTION_MESSAGE,
                exception
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
