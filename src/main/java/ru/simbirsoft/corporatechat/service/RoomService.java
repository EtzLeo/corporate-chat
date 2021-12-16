package ru.simbirsoft.corporatechat.service;

import ru.simbirsoft.corporatechat.domain.dto.RoomRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.RoomResponseDto;

public interface RoomService {

    RoomResponseDto findById(Long id);
    RoomResponseDto editRoom(Long id, RoomRequestDto roomRequestDto);
    RoomResponseDto createRoom(RoomRequestDto roomRequestDto);
    void deleteRoomById(Long id);
}
