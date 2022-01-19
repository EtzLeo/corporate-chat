package ru.simbirsoft.corporatechat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.corporatechat.domain.Room;
import ru.simbirsoft.corporatechat.domain.UserRoomFK;
import ru.simbirsoft.corporatechat.domain.dto.RoomRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.RoomResponseDto;
import ru.simbirsoft.corporatechat.domain.enums.Role;
import ru.simbirsoft.corporatechat.domain.enums.RoomType;
import ru.simbirsoft.corporatechat.exception.IllegalDataException;
import ru.simbirsoft.corporatechat.exception.ResourceNotFoundException;
import ru.simbirsoft.corporatechat.mapper.RoomMapper;
import ru.simbirsoft.corporatechat.repository.RoomRepository;
import ru.simbirsoft.corporatechat.service.RoomService;
import ru.simbirsoft.corporatechat.service.UserRoomService;
import ru.simbirsoft.corporatechat.util.AuthUtil;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final UserRoomService userRoomService;
    private final RoomMapper mapper;

    @Transactional
    @Override
    public RoomResponseDto findById(Long id) {
        return roomRepository
                .findById(id)
                .map(mapper::roomToRoomResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
    }

    @Transactional
    @Override
    public RoomResponseDto renameRoom(Long id, String name) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        if(!Objects.equals(AuthUtil.getCurrentUser().getUsername(),room.getOwner().getName())) {
            throw new AccessDeniedException("Only admin can rename room");
        }
        room.setName(name);
        roomRepository.save(room);
        return mapper.roomToRoomResponseDto(room);
    }

    @Transactional
    @Override
    public RoomResponseDto createRoom(RoomRequestDto roomRequestDto) {
        Room room = mapper.roomRequestDtoToRoom(roomRequestDto);
        final int[] usersCount = {roomRequestDto.getUsers().size()};

        roomRequestDto.getUsers().forEach(id -> {
            if (Objects.equals(AuthUtil.getCurrentUser().getId(), id)) {
                usersCount[0]--;
            }
        });

        if (usersCount[0] > 1) {
            room.setType(RoomType.PUBLIC);
        }
        else {
            if (usersCount[0] == 0)
                throw new IllegalDataException("Invalid number of users");
            room.setType(RoomType.PRIVATE);
        }

        roomRepository.save(room);

        for (Long id : roomRequestDto.getUsers()) {
            userRoomService.register(new UserRoomFK(id, room.getId()), Role.ROLE_USER);
        }
        userRoomService.register(new UserRoomFK(AuthUtil.getCurrentUser().getId(), room.getId()), Role.ROLE_ADMIN);

        return mapper.roomToRoomResponseDto(room);
    }

    @Transactional
    @Override
    public RoomResponseDto createRoomOnRegistration(RoomRequestDto roomRequestDto) {
        Room room = roomRepository.save(mapper.roomRequestDtoToRoom(roomRequestDto));
        return mapper.roomToRoomResponseDto(room);
    }


    @Transactional
    @Override
    public void deleteRoomById(Long id) {
        Room room = roomRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Room not found"));

        if(!Objects.equals(AuthUtil.getCurrentUser().getUsername(),room.getOwner().getName())) {
            throw new AccessDeniedException("Only owner can delete room");
        }
        roomRepository.delete(room);
    }

}
