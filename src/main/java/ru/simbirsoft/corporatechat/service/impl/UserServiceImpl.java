package ru.simbirsoft.corporatechat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.corporatechat.domain.User;
import ru.simbirsoft.corporatechat.domain.dto.UserRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.UserResponseDto;
import ru.simbirsoft.corporatechat.exception.UserNotFoundException;
import ru.simbirsoft.corporatechat.mapper.UserMapper;
import ru.simbirsoft.corporatechat.repository.UserRepository;
import ru.simbirsoft.corporatechat.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public UserResponseDto findById(Long id) {
        return userRepository
                .findById(id)
                .map(mapper::userToUserResponseDto)
                .orElseThrow(() -> new UserNotFoundException("user not found: " + id));
    }

    @Override
    public UserResponseDto editUser(Long id, UserRequestDto userRequestDto) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()){
            User user = mapper.userRequestDtoToUser(userRequestDto);
            user.setId(id);
            userRepository.save(user);
            return mapper.userToUserResponseDto(user);
        }
        throw new UserNotFoundException("user not found: " + id);
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = userRepository.save(mapper.userRequestDtoToUser(userRequestDto));
        return mapper.userToUserResponseDto(user);
    }

    @Override
    public void deleteUserById(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException("user not found: " + id));

        userRepository.delete(user);
    }
}
