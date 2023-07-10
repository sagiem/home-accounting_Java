package ru.sagiem.homeaccounting.dtos;

import lombok.Data;

@Data
public class UserCreateDto {
    private long id;
    private String username;
    private String email;
}
