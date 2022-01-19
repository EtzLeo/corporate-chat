package ru.simbirsoft.corporatechat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.corporatechat.domain.User;
import ru.simbirsoft.corporatechat.domain.dto.UserRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.UserResponseDto;
import ru.simbirsoft.corporatechat.exception.AccessDeniedException;
import ru.simbirsoft.corporatechat.exception.ResourceNotFoundException;
import ru.simbirsoft.corporatechat.mapper.UserMapper;
import ru.simbirsoft.corporatechat.repository.UserRepository;
import ru.simbirsoft.corporatechat.service.UserService;
import ru.simbirsoft.corporatechat.util.AuthUtil;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserResponseDto register(UserRequestDto user) {
        User registeredUser = mapper.userRequestDtoToUser(user);
        registeredUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(registeredUser);
        return mapper.userToUserResponseDto(registeredUser);
    }

    @Transactional
    @Override
    public UserResponseDto findById(Long id) {
        UserResponseDto user =  userRepository
                .findById(id)
                .map(mapper::userToUserResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if(!AuthUtil.resolveUser(user.getName()))
            throw new AccessDeniedException("User search not allowed");

        return user;
    }

    @Transactional
    @Override
    public UserResponseDto findByName(String name) {
        return userRepository
                .findByName(name)
                .map(mapper::userToUserResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Transactional
    @Override
    public UserResponseDto editUser(Long id, UserRequestDto userRequestDto) {
        if (!AuthUtil.resolveUser(userRequestDto.getName()))
            throw new AccessDeniedException("User modification not allowed");

        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = mapper.userRequestDtoToUser(userRequestDto);
            user.setId(id);
            userRepository.save(user);
            return mapper.userToUserResponseDto(user);
        }

        else throw new ResourceNotFoundException("User not found");
    }

    @Transactional
    @Override
    public void deleteUserById(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not found"));

        if(!AuthUtil.resolveUser(user.getName()))
            throw new AccessDeniedException("User deletion not allowed");

        userRepository.delete(user);
    }

}
