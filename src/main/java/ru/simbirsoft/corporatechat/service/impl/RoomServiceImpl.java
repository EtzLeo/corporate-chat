package ru.simbirsoft.corporatechat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.corporatechat.domain.Room;
import ru.simbirsoft.corporatechat.domain.dto.RoomRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.RoomResponseDto;
import ru.simbirsoft.corporatechat.exception.ResourceNotFoundException;
import ru.simbirsoft.corporatechat.mapper.RoomMapper;
import ru.simbirsoft.corporatechat.repository.RoomRepository;
import ru.simbirsoft.corporatechat.service.RoomService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper mapper;

    @Override
    public RoomResponseDto findById(Long id) {
        return roomRepository
                .findById(id)
                .map(mapper::roomToRoomResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("room not found: " + id));
    }

    @Override
    public RoomResponseDto editRoom(Long id, RoomRequestDto roomRequestDto) {
        Optional<Room> roomOptional = roomRepository.findById(id);
        if (roomOptional.isPresent()){
            Room room = mapper.roomRequestDtoToRoom(roomRequestDto);
            room.setId(id);
            roomRepository.save(room);
            return mapper.roomToRoomResponseDto(room);
        }
        throw new ResourceNotFoundException("room not found: " + id);
    }

    @Override
    public RoomResponseDto createRoom(RoomRequestDto roomRequestDto) {
        Room room = roomRepository.save(mapper.roomRequestDtoToRoom(roomRequestDto));
        return mapper.roomToRoomResponseDto(room);
    }

    @Override
    public void deleteRoomById(Long id) {
        Room room = roomRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("room not found: " + id));

        roomRepository.delete(room);
    }
}
