package ru.simbirsoft.corporatechat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.corporatechat.config.jwt.JwtUserDto;
import ru.simbirsoft.corporatechat.domain.User;
import ru.simbirsoft.corporatechat.domain.UserRoomFK;
import ru.simbirsoft.corporatechat.domain.dto.*;
import ru.simbirsoft.corporatechat.domain.enums.Role;
import ru.simbirsoft.corporatechat.domain.enums.RoomType;
import ru.simbirsoft.corporatechat.exception.IllegalDataException;
import ru.simbirsoft.corporatechat.config.jwt.JwtTokenProvider;
import ru.simbirsoft.corporatechat.exception.ResourceNotFoundException;
import ru.simbirsoft.corporatechat.repository.UserRepository;
import ru.simbirsoft.corporatechat.service.RoomService;
import ru.simbirsoft.corporatechat.service.UserRoomService;
import ru.simbirsoft.corporatechat.service.UserService;
import ru.simbirsoft.corporatechat.service.impl.AuthService;

import java.util.*;

@RestController
@RequestMapping(value = "/api/auth")
@RequiredArgsConstructor
@Log
@Validated
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam @Nullable Long id, @RequestBody JwtUserDto requestDto) {
        try {
            Map<Object, Object> response = authService.login(id,requestDto);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody JwtUserDto dto) {
        try{
            authService.register(dto);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    //TODO logout

}
