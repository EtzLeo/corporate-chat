package ru.simbirsoft.corporatechat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.corporatechat.domain.dto.RoomRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.RoomResponseDto;
import ru.simbirsoft.corporatechat.repository.RoomRepository;
import ru.simbirsoft.corporatechat.service.RoomService;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public RoomResponseDto findById(Long id) {
        return null;
    }

    @Override
    public RoomResponseDto editRoom(Long id, RoomRequestDto roomRequestDto) {
        return null;
    }

    @Override
    public RoomResponseDto createRoom(RoomRequestDto roomRequestDto) {
        return null;
    }

    @Override
    public boolean deleteRoomById(Long id) {
        return false;
    }
}
