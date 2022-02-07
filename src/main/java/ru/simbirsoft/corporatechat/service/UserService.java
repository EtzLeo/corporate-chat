package ru.simbirsoft.corporatechat.service;

import ru.simbirsoft.corporatechat.domain.dto.UserRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.UserResponseDto;

public interface UserService {
    UserResponseDto findById(Long id);
    UserResponseDto findByName(String name);
    UserResponseDto editUser(Long id, UserRequestDto userRequestDto);
    UserResponseDto register(UserRequestDto user);
    void deleteUserById(Long id);
}
