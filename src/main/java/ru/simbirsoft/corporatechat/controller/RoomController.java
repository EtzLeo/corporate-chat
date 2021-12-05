package ru.simbirsoft.corporatechat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.corporatechat.domain.dto.RoomRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.RoomResponseDto;
import ru.simbirsoft.corporatechat.service.RoomService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/room")
public class RoomController {
    public final RoomService roomService;

    @GetMapping
    public RoomResponseDto getRoom(@RequestParam Long id) {
        if (id!=null) {
            return roomService.findById(id);
        }
        return null;
    }

    @PostMapping
    public RoomResponseDto createRoom(@RequestBody RoomRequestDto roomRequestDto) {
        if (roomRequestDto!=null) {
            return roomService.createRoom(roomRequestDto);
        }
        return null;

    }

    @PatchMapping
    public RoomResponseDto updateRoom(@RequestParam Long id, @RequestBody RoomRequestDto roomRequestDto) {
        if (roomRequestDto!=null) {
            return roomService.editRoom(id, roomRequestDto);
        }
        return null;
    }

    @DeleteMapping
    public boolean deleteRoom(@RequestParam Long id) {
        if (id!=null) {
            return roomService.deleteRoomById(id);
        }
        return false;
    }
}
