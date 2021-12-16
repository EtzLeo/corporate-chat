package ru.simbirsoft.corporatechat.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private long id;

    private String name;

    private String password;

//    private Set<Long> rooms;
}
