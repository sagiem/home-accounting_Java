package ru.sagiem.whattobuy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FamilyGroupNotDeleteCreateUserException extends RuntimeException{
}
