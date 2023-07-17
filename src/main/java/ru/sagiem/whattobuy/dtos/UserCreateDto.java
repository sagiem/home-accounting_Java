package ru.sagiem.whattobuy.dtos;

import lombok.Data;

@Data
public class UserCreateDto {
    private long id;
    private String username;
    private String email;
}
