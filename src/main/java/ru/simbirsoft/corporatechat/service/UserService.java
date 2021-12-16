package ru.simbirsoft.corporatechat.service;

import ru.simbirsoft.corporatechat.domain.dto.UserRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.UserResponseDto;

public interface UserService {

    UserResponseDto findById(Long id);
    UserResponseDto editUser(Long id, UserRequestDto userRequestDto);
    UserResponseDto createUser(UserRequestDto userRequestDto);
    void deleteUserById(Long id);
}
