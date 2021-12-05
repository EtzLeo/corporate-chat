package ru.simbirsoft.corporatechat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.corporatechat.domain.dto.UserRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.UserResponseDto;
import ru.simbirsoft.corporatechat.repository.UserRepository;
import ru.simbirsoft.corporatechat.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDto findById(Long id) {
        return null;
    }

    @Override
    public UserResponseDto editUser(Long id, UserRequestDto userRequestDto) {
        return null;
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        return null;
    }

    @Override
    public boolean deleteUserById(Long id) {
        return false;
    }
}
