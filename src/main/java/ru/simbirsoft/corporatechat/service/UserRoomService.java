package ru.simbirsoft.corporatechat.service;

import ru.simbirsoft.corporatechat.domain.Room;
import ru.simbirsoft.corporatechat.domain.User;
import ru.simbirsoft.corporatechat.domain.UserRoomFK;
import ru.simbirsoft.corporatechat.domain.dto.RoomResponseDto;
import ru.simbirsoft.corporatechat.domain.dto.UserResponseDto;
import ru.simbirsoft.corporatechat.domain.dto.UserRoomRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.UserRoomResponseDto;
import ru.simbirsoft.corporatechat.domain.enums.Role;

public interface UserRoomService {
    UserRoomResponseDto findById(UserRoomFK id);
    UserRoomResponseDto getById(UserRoomFK id);
    UserRoomResponseDto add(UserRoomFK id);
    UserRoomResponseDto register(UserRoomFK id, Role role);
    UserRoomResponseDto expel(UserRoomFK id);
    UserRoomResponseDto setBlock(UserRoomFK id, UserRoomRequestDto dto);
    UserRoomResponseDto setRole(UserRoomFK id, Role role);
}
