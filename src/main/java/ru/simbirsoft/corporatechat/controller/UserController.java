package ru.simbirsoft.corporatechat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.corporatechat.domain.dto.UserRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.UserResponseDto;
import ru.simbirsoft.corporatechat.service.UserService;

import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Log
public class UserController {

    private final UserService userService;

    @GetMapping
    public UserResponseDto getUser(@RequestParam @NotNull Long id) {
        if (id!=null) {
            return userService.findById(id);
        }
        return null;
    }

    @PatchMapping
    public UserResponseDto updateUser(@RequestParam @NotNull Long id,
                                      @RequestBody @NotNull UserRequestDto userRequestDto) {
        if (userRequestDto!=null && id!=null) {
            return userService.editUser(id, userRequestDto);
        }
        return null;
    }

//    @DeleteMapping
//    public void deleteUser(@RequestParam @NotNull Long id) {
//        if (id!=null) {
//            userService.deleteUserById(id);
//        }
//    }
}
