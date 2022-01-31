package ru.simbirsoft.corporatechat.service;

import ru.simbirsoft.corporatechat.domain.UserRoomFK;
import ru.simbirsoft.corporatechat.domain.dto.UserRoomRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.UserRoomResponseDto;
import ru.simbirsoft.corporatechat.domain.enums.Role;

public interface UserRoomService {
    UserRoomResponseDto findById(UserRoomFK id);
    UserRoomResponseDto getById(UserRoomFK id);
    UserRoomResponseDto addUser(UserRoomFK id);
    UserRoomResponseDto registerUser(UserRoomFK id, Role role);
    UserRoomResponseDto expelUser(UserRoomFK id);
    UserRoomResponseDto exitRoom(Long roomId);
    UserRoomResponseDto setBlock(UserRoomFK id, UserRoomRequestDto dto);
    UserRoomResponseDto setRole(UserRoomFK id, Role role);
}
