package ru.simbirsoft.corporatechat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.corporatechat.domain.dto.UserRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.UserResponseDto;
import ru.simbirsoft.corporatechat.service.UserService;

import javax.validation.Valid;
import java.util.logging.Level;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Log
public class UserController {

    public final UserService userService;

    @GetMapping
    public UserResponseDto getUser(@RequestParam Long id) {
        if (id!=null) {
            return userService.findById(id);
        }
        return null;
    }

    @PostMapping("/registration")
    public UserResponseDto createUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        return userService.register(userRequestDto);
    }

    @PatchMapping
    public UserResponseDto updateUser(@RequestParam Long id, @RequestBody UserRequestDto userRequestDto) {
        if (userRequestDto!=null && id!=null) {
            return userService.editUser(id, userRequestDto);
        }
        return null;
    }

    @DeleteMapping
    public void deleteUser(@RequestParam Long id) {
        if (id!=null) {
            userService.deleteUserById(id);
        }
    }
}
