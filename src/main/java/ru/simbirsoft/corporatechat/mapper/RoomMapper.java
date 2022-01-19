package ru.simbirsoft.corporatechat.mapper;

import org.mapstruct.*;
import ru.simbirsoft.corporatechat.domain.Room;
import ru.simbirsoft.corporatechat.domain.User;
import ru.simbirsoft.corporatechat.domain.dto.RoomRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.RoomResponseDto;


@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "ownerId", source = "owner.id"),
            @Mapping(target = "type", source = "type"),
            @Mapping(target = "users",source = "users", qualifiedByName = "userToId")

    })
    RoomResponseDto roomToRoomResponseDto(Room entity);

    @Mappings({
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "owner.id", source = "ownerId"),
            @Mapping(target = "type", source = "type"),
            @Mapping(target = "users", source = "users", qualifiedByName = "idToUser")

    })
    Room roomRequestDtoToRoom(RoomRequestDto dto);


    @Named("userToId")
    public static Long userToId(User entity) {
        return entity.getId();
    }

    @Named("idToUser")
    public static User idToUser(Long id) {
        return new User(id);
    }

}
