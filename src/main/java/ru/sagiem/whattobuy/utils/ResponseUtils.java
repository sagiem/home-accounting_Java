package ru.sagiem.whattobuy.utils;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import ru.sagiem.whattobuy.dto.ExceptionResponse;
import ru.sagiem.whattobuy.dto.SuccessResponse;
import ru.sagiem.whattobuy.exception.FamilyGroupInvitationNotCreatorAndUserException;
import ru.sagiem.whattobuy.exception.FamilyGroupInvitationNotFoundException;
import ru.sagiem.whattobuy.exception.FamilyGroupNotCreatorException;

@UtilityClass
public class ResponseUtils {

    public static final String DATE_PATTERN = "dd-MM-yyyy";
    public static final String CREATION_MESSAGE = "The %s have been successful created";
    public static final String UPDATE_MESSAGE = "Объект %s был успешно обновлен";
    public static final String SEND_INVITATION = "Приглашение в %s успешно отправлено";
    public static final String DELETE_INVITATION = "Приглашение в %s успешно удалено";
    public static final String ACCEPT_INVITATION = "Приглашение в %s успешно принято";
    public static final String FAMILY_GROUP_INFITATIONS_NOT_CREATOR_AND_USER_EXCEPTION_MESSAGE = "Вы не отсылали либо вам не отправляли это приглашение";
    public static final String FAMILY_GROUP_INFITATIONS_NOT_USER_EXCEPTION_MESSAGE = "Вы не являетесь получателем приглашения";
    public static final String FAMILY_GROUP_INFITATIONS_NOT_FOUND_EXCEPTION_MESSAGE = "Приглашение не найдено";
    public static final String DELETE_USER_IN_GROUP = "Пользователь %s успешно удален";
    public static final String DELETE_MESSAGE = "Объект %s успешно удален";
    public static final String CHANGE_ROLE_MESSAGE = "Роль из %s была успешно изменена";
    public static final String USER_NOT_FOUND_EXCEPTION_MESSAGE = "Пользователь не найден";
    public static final String FAMILY_GROUP_NOT_FOUND_EXCEPTION_MESSAGE = "Группа не найдена";
    public static final String FAMILY_GROUP_ALREADY_USER_EXCEPTION_MESSAGE = "Пользователь уже в группе";
    public static final String FAMILY_GROUP_NOT_CREATOR_EXCEPTION_MESSAGE = "Пользователь не является владельцем группы";
    public static final String FAMILY_GROUP_NOT_USER_EXCEPTION_MESSAGE = "Пользователь не состоит в группе";
    public static final String FAMILY_GROUP_NOT_DELETE_CREATED_USER_EXCEPTION_MESSAGE = "Нельзя удалить владельца группы";
    public static final String FAMILY_GROUP_DELETE_MESSAGE = "Группа %s успешно удалена";
    public static final String POINT_SHOPING_UPDATE_MESSAGE = "Точка покупки %s успешно обновлена";
    public static final String CATEGORY_PRODUCT_NOT_FOUND_EXCEPTION_MESSAGE = "Категория продукта не найдена";
    public static final String CATEGORY_PRODUCT_UPDATE_MESSAGE = "Категория успешно обновлена, новое имя: %s";
    public static final String CATEGORY_PRODUCT_DELETE_MESSAGE = "Категория %s успешно удалена";


    public static final String SUBCATEGORY_PRODUCT_NOT_FOUND_EXCEPTION_MESSAGE = "Подкатегория продукта не найдена";
    public static final String SUBCATEGORY_PRODUCT_UPDATE_MESSAGE = "Подкатегория успешно обновлена, новое имя: %s";
    public static final String SUBCATEGORY_PRODUCT_DELETE_MESSAGE = "Подкатегория %s успешно удалена";

    public static final String PRODUCT_NOT_FOUND_EXCEPTION_MESSAGE = "Продукт не найден";
    public static final String PRODUCT_UPDATE_MESSAGE = "продукт %s успешно обновлен";
    public static final String PRODUCT_DELETE_MESSAGE = "Продукт %s успешно удален";

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

    public static SuccessResponse getSuccessResponse(String message, String className) {
        return new SuccessResponse(HttpStatus.OK, String.format(message, className), className);
    }
}
