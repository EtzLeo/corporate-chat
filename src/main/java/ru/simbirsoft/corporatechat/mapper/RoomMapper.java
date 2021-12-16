package ru.simbirsoft.corporatechat.mapper;

import org.mapstruct.*;
import ru.simbirsoft.corporatechat.domain.Room;
import ru.simbirsoft.corporatechat.domain.dto.RoomRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.RoomResponseDto;


@Mapper(componentModel = "spring"
        , uses = {UserMapper.class})
public interface RoomMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "ownerId", source = "owner.id"),
            @Mapping(target = "type", source = "type"),
//            @Mapping(target = "users",source = "users")

    })
    RoomResponseDto roomToRoomResponseDto(Room entity);

    @Mappings({
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "owner.id", source = "ownerId"),
            @Mapping(target = "type", source = "type"),
//            @Mapping(target = "users", source = "users", qualifiedByName = "userList")

    })
    Room roomRequestDtoToRoom(RoomRequestDto dto);
}
