package ru.simbirsoft.corporatechat.service;

import ru.simbirsoft.corporatechat.domain.dto.RoomRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.RoomResponseDto;

public interface RoomService {
    RoomResponseDto findById(Long id);
    RoomResponseDto findByName(String name);
    RoomResponseDto renameRoom(Long id, String name);
    RoomResponseDto createRoom(RoomRequestDto roomRequestDto);
    RoomResponseDto createRoomOnRegistration(RoomRequestDto roomRequestDto);
    void deleteRoomById(Long id);
}
