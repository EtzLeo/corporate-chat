package ru.simbirsoft.corporatechat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.simbirsoft.corporatechat.domain.User;
import ru.simbirsoft.corporatechat.domain.UserRoom;
import ru.simbirsoft.corporatechat.domain.dto.UserRequestDto;
import ru.simbirsoft.corporatechat.exception.ResourceNotFoundException;
import ru.simbirsoft.corporatechat.repository.UserRepository;
import ru.simbirsoft.corporatechat.config.jwt.JwtTokenProvider;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/auth")
@RequiredArgsConstructor
@Log
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequestDto requestDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getName(), requestDto.getPassword()));
            User user = userRepository.findByName(requestDto.getName()).orElseThrow(() ->
                    new ResourceNotFoundException("user not found: " + requestDto.getName()));

            String token = jwtTokenProvider.createToken(user.getName(), user.getUserRooms().stream().map(UserRoom::getRole).collect(Collectors.toList()));

            Map<Object, Object> response = new HashMap<>();
            response.put("name", requestDto.getName());
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
