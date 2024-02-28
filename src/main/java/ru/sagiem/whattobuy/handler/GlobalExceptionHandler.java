package ru.sagiem.whattobuy.handler;

import static ru.sagiem.whattobuy.utils.ResponseUtils.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.sagiem.whattobuy.dto.ExceptionResponse;
import ru.sagiem.whattobuy.exception.*;
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

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(UsernameNotFoundException exception) {
        ExceptionResponse response = ResponseUtils.getExceptionResponse(
                HttpStatus.NOT_FOUND,
                USER_NOT_FOUND_EXCEPTION_MESSAGE,
                exception
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FamilyGroupNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(FamilyGroupNotFoundException exception) {
        ExceptionResponse response = ResponseUtils.getExceptionResponse(
                HttpStatus.NOT_FOUND,
                FAMILY_GROUP_NOT_FOUND_EXCEPTION_MESSAGE,
                exception
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FamilyGroupAlreadyUserException.class)
    public ResponseEntity<ExceptionResponse> handleException(FamilyGroupAlreadyUserException exception) {
        ExceptionResponse response = ResponseUtils.getExceptionResponse(
                HttpStatus.CONFLICT,
                FAMILY_GROUP_ALREADY_USER_EXCEPTION_MESSAGE,
                exception
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FamilyGroupNotCreatorException.class)
    public ResponseEntity<ExceptionResponse> handleException(FamilyGroupNotCreatorException exception) {
        ExceptionResponse response = ResponseUtils.getExceptionResponse(
                HttpStatus.CONFLICT,
                FAMILY_GROUP_NOT_CREATOR_EXCEPTION_MESSAGE,
                exception
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FamilyGroupNotUserException.class)
    public ResponseEntity<ExceptionResponse> handleException(FamilyGroupNotUserException exception) {
        ExceptionResponse response = ResponseUtils.getExceptionResponse(
                HttpStatus.CONFLICT,
                FAMILY_GROUP_NOT_USER_EXCEPTION_MESSAGE,
                exception
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FamilyGroupInvitationNotCreatorAndUserException.class)
    public ResponseEntity<ExceptionResponse> handleException(FamilyGroupInvitationNotCreatorAndUserException exception) {
        ExceptionResponse response = ResponseUtils.getExceptionResponse(
                HttpStatus.CONFLICT,
                FAMILY_GROUP_INFITATIONS_NOT_CREATOR_AND_USER_EXCEPTION_MESSAGE,
                exception
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FamilyGroupInvitationNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(FamilyGroupInvitationNotFoundException exception) {
        ExceptionResponse response = ResponseUtils.getExceptionResponse(
                HttpStatus.NOT_FOUND,
                FAMILY_GROUP_INFITATIONS_NOT_FOUND_EXCEPTION_MESSAGE,
                exception
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
