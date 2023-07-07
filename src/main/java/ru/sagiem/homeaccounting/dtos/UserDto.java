package ru.sagiem.homeaccounting.dtos;

import lombok.Data;

@Data
public class UserDto {
    private long id;
    private String username;
    private String email;
}
