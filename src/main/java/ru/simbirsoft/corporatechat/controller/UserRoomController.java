package ru.simbirsoft.corporatechat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.corporatechat.domain.UserRoomFK;
import ru.simbirsoft.corporatechat.domain.dto.UserRoomRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.UserRoomResponseDto;
import ru.simbirsoft.corporatechat.domain.enums.Role;
import ru.simbirsoft.corporatechat.service.UserRoomService;

import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/room/{roomId}")
public class UserRoomController {

    private final UserRoomService userRoomService;

    @GetMapping
    public UserRoomResponseDto getById(@PathVariable Long roomId,
                                       @RequestParam @NotNull Long userId) {
        return userRoomService.getById(new UserRoomFK(userId, roomId));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_MODER', 'ROLE_ADMIN')")
    @PatchMapping("/setBlock")
    UserRoomResponseDto setBlock(@PathVariable Long roomId,
                                 @RequestParam @NotNull Long userId,
                                 @RequestBody @NotNull UserRoomRequestDto dto) {
       return userRoomService.setBlock(new UserRoomFK(userId, roomId),dto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_MODER', 'ROLE_ADMIN')")
    @PatchMapping("/setRole")
    UserRoomResponseDto setRole(@PathVariable Long roomId,
                                @RequestParam @NotNull Long userId,
                                @RequestParam @NotNull Role role) {
        return userRoomService.setRole(new UserRoomFK(userId, roomId), role);
    }

    @PostMapping
    UserRoomResponseDto addUser(@PathVariable Long roomId,
                                @RequestParam @NotNull Long userId) {
        return userRoomService.add(new UserRoomFK(userId, roomId));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping
    UserRoomResponseDto expelUser(@PathVariable Long roomId,
                                  @RequestParam @NotNull Long userId) {
        return userRoomService.expel(new UserRoomFK(userId, roomId));
    }

}
