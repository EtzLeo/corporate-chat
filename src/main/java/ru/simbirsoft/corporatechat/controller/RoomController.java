package ru.simbirsoft.corporatechat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.corporatechat.domain.dto.RoomRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.RoomResponseDto;
import ru.simbirsoft.corporatechat.service.RoomService;

import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/room")
public class RoomController {
    public final RoomService roomService;

    @GetMapping
    public RoomResponseDto getRoom(@RequestParam @NotNull Long id) {
        return roomService.findById(id);
    }

    @PostMapping
    public RoomResponseDto createRoom(@RequestBody @NotNull RoomRequestDto roomRequestDto) {
        return roomService.createRoom(roomRequestDto);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping
    public RoomResponseDto renameRoom(@RequestParam @NotNull Long id,
                                      @RequestParam @NotNull String name) {
        return roomService.renameRoom(id, name);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping
    public void deleteRoom(@RequestParam @NotNull Long id) {
        if (id!=null) {
            roomService.deleteRoomById(id);
        }
    }
}
