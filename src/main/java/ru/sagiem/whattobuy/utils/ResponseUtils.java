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
    public static final String CHANGE_ROLE_MESSAGE = "Роль из %s была успешно изменена";
    public static final String BAD_CREDENTIALS_EXCEPTION_MESSAGE = "Неверное имя пользователя и пароль";
    public static final String METHOD_ARGUMENT_NOT_VALID_EXCEPTION_MESSAGE = "Переданные данные не прошли проверку";
    public static final String DATA_SOURCE_LOOKUP_FAILURE_EXCEPTION_MESSAGE = "Источник данных получить не удалось";
    public static final String DATA_INTEGRITY_VIOLATION_EXCEPTION_MESSAGE = "Входные данные не соответствуют требуемым критериям";
    public static final String HTTP_MESSAGE_NOT_READABLE_EXCEPTION_MESSAGE = "Введенные данные неверны и приводят к ошибке";
    public static final String ENTITY_NOT_FOUND_EXCEPTION_MESSAGE = "Укажите введенные данные";
    public static final String BOOK_NOT_FOUND_EXCEPTION_MESSAGE = "Книга с этим идентификатором не найдена";
    public static final String JPA_OBJECT_RETRIEVAL_FAILURE_EXCEPTION_MESSAGE = "Введенные данные нарушают установленные требования";
    public static final String ACCESS_DENIED_EXCEPTION_MESSAGE = "У вас нет права доступа";
    public static final String MALFORMED_JWT_EXCEPTION_MESSAGE = "Incorrect token";
    public static final String EXPIRED_JWT_EXCEPTION_MESSAGE = "Жизненный цикл токена завершен";
    public static final String SIGNATURE_EXCEPTION_MESSAGE = "Неправильная подпись";

    public static ExceptionResponse getExceptionResponse(HttpStatus status, String message, Exception exception) {
        return new ExceptionResponse(status, message, exception.getClass().getSimpleName());
    }
}
