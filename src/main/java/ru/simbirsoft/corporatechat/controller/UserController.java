package ru.simbirsoft.corporatechat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.corporatechat.domain.dto.UserRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.UserResponseDto;
import ru.simbirsoft.corporatechat.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    public final UserService userService;

    @GetMapping
    public UserResponseDto getUser(@RequestParam Long id) {
        if (id!=null) {
            return userService.findById(id);
        }
        return null;
    }

    @PostMapping
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
        if (userRequestDto!=null) {
            return userService.createUser(userRequestDto);
        }
        return null;
    }

    @PatchMapping
    public UserResponseDto updateUser(@RequestParam Long id, @RequestBody UserRequestDto userRequestDto) {
        if (userRequestDto!=null && id!=null) {
            return userService.editUser(id, userRequestDto);
        }
        return null;
    }

    @DeleteMapping
    public boolean deleteUser(@RequestParam Long id) {
        if (id!=null) {
            return userService.deleteUserById(id);
        }
        return false;
    }
}
