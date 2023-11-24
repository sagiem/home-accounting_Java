package ru.sagiem.whattobuy.utils;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import ru.sagiem.whattobuy.dto.ExceptionResponse;

@UtilityClass
public class ResponseUtils {

    public static final String DATE_PATTERN = "dd-MM-yyyy";
    public static final String CREATION_MESSAGE = "The %s have been successful created";
    public static final String UPDATE_MESSAGE = "The %s have been successful updated";
    public static final String DELETE_MESSAGE = "The %s have been successful deleted";
    public static final String CHANGE_ROLE_MESSAGE = "The role from %s have been successful changed";
    public static final String BAD_CREDENTIALS_EXCEPTION_MESSAGE = "Incorrect username and password";
    public static final String METHOD_ARGUMENT_NOT_VALID_EXCEPTION_MESSAGE = "The transmitted data did not pass verification";
    public static final String DATA_SOURCE_LOOKUP_FAILURE_EXCEPTION_MESSAGE = "Data source could not be obtained";
    public static final String DATA_INTEGRITY_VIOLATION_EXCEPTION_MESSAGE = "The input data does not correspond to the required";
    public static final String HTTP_MESSAGE_NOT_READABLE_EXCEPTION_MESSAGE = "The entered data is incorrect and leads to error";
    public static final String ENTITY_NOT_FOUND_EXCEPTION_MESSAGE = "Specify the entered data";
    public static final String BOOK_NOT_FOUND_EXCEPTION_MESSAGE = "Book with this id is not found";
    public static final String JPA_OBJECT_RETRIEVAL_FAILURE_EXCEPTION_MESSAGE = "The data entered violates the established requirements";
    public static final String ACCESS_DENIED_EXCEPTION_MESSAGE = "You don`t have access right";
    public static final String MALFORMED_JWT_EXCEPTION_MESSAGE = "Incorrect token";
    public static final String EXPIRED_JWT_EXCEPTION_MESSAGE = "The lifecycle of the token is completed";
    public static final String SIGNATURE_EXCEPTION_MESSAGE = "Incorrect signature";

    public static ExceptionResponse getExceptionResponse(HttpStatus status, String message, Exception exception) {
        return new ExceptionResponse(status, message, exception.getClass().getSimpleName());
    }
}
