package ru.simbirsoft.corporatechat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.simbirsoft.corporatechat.domain.User;
import ru.simbirsoft.corporatechat.domain.dto.UserRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.UserResponseDto;
import ru.simbirsoft.corporatechat.exception.ResourceNotFoundException;
import ru.simbirsoft.corporatechat.mapper.UserMapper;
import ru.simbirsoft.corporatechat.repository.UserRepository;
import ru.simbirsoft.corporatechat.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(UserRequestDto user) {
        User registeredUser = mapper.userRequestDtoToUser(user);
        registeredUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(registeredUser);
        return mapper.userToUserResponseDto(registeredUser);
    }

    @Override
    public UserResponseDto findById(Long id) {
        return userRepository
                .findById(id)
                .map(mapper::userToUserResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("user not found: " + id));
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
        throw new ResourceNotFoundException("user not found: " + id);
    }

    @Override
    public void deleteUserById(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("user not found: " + id));

        userRepository.delete(user);
    }
}
