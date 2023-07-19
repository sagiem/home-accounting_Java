package ru.sagiem.whattobuy.dto;

import lombok.Data;

@Data
public class UserCreateDto {
    private long id;
    private String username;
    private String email;
}
