package ru.sagiem.whattobuy.handler;

import static ru.sagiem.whattobuy.utils.ResponseUtils.*;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
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
                HttpStatus.NOT_FOUND,
                FAMILY_GROUP_ALREADY_USER_EXCEPTION_MESSAGE,
                exception
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FamilyGroupNotCreatorException.class)
    public ResponseEntity<ExceptionResponse> handleException(FamilyGroupNotCreatorException exception) {
        ExceptionResponse response = ResponseUtils.getExceptionResponse(
                HttpStatus.NOT_FOUND,
                FAMILY_GROUP_NOT_CREATOR_EXCEPTION_MESSAGE,
                exception
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FamilyGroupNotUserException.class)
    public ResponseEntity<ExceptionResponse> handleException(FamilyGroupNotUserException exception) {
        ExceptionResponse response = ResponseUtils.getExceptionResponse(
                HttpStatus.NOT_FOUND,
                FAMILY_GROUP_NOT_USER_EXCEPTION_MESSAGE,
                exception
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FamilyGroupInvitationNotCreatorAndUserException.class)
    public ResponseEntity<ExceptionResponse> handleException(FamilyGroupInvitationNotCreatorAndUserException exception) {
        ExceptionResponse response = ResponseUtils.getExceptionResponse(
                HttpStatus.NOT_FOUND,
                FAMILY_GROUP_INFITATIONS_NOT_CREATOR_AND_USER_EXCEPTION_MESSAGE,
                exception
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
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

    @ExceptionHandler(FamilyGroupInvitationNotUserException.class)
    public ResponseEntity<ExceptionResponse> handleException(FamilyGroupInvitationNotUserException exception) {
        ExceptionResponse response = ResponseUtils.getExceptionResponse(
                HttpStatus.NOT_FOUND,
                FAMILY_GROUP_INFITATIONS_NOT_USER_EXCEPTION_MESSAGE,
                exception
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FamilyGroupNotDeleteCreateUserException.class)
    public ResponseEntity<ExceptionResponse> handleException(FamilyGroupNotDeleteCreateUserException exception) {
        ExceptionResponse response = ResponseUtils.getExceptionResponse(
                HttpStatus.NOT_FOUND,
                FAMILY_GROUP_NOT_DELETE_CREATED_USER_EXCEPTION_MESSAGE,
                exception
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(FamilyGroupNotDeleteCreateUserException.class)
//    public ProblemDetail handleException(FamilyGroupNotDeleteCreateUserException exception) {
//        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
//                        FAMILY_GROUP_NOT_DELETE_CREATED_USER_EXCEPTION_MESSAGE);
//    }

    @ExceptionHandler(CategoryProductNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(CategoryProductNotFoundException exception) {
        ExceptionResponse response = ResponseUtils.getExceptionResponse(
                HttpStatus.NOT_FOUND,
                CATEGORY_PRODUCT_NOT_FOUND_EXCEPTION_MESSAGE,
                exception
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(ProductNotFoundException exception) {
        ExceptionResponse response = ResponseUtils.getExceptionResponse(
                HttpStatus.NOT_FOUND,
                PRODUCT_NOT_FOUND_EXCEPTION_MESSAGE,
                exception
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SubCategoryProductNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(SubCategoryProductNotFoundException exception) {
        ExceptionResponse response = ResponseUtils.getExceptionResponse(
                HttpStatus.NOT_FOUND,
                SUBCATEGORY_PRODUCT_NOT_FOUND_EXCEPTION_MESSAGE,
                exception
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
