package ru.simbirsoft.corporatechat.mapper;

import org.mapstruct.*;
import ru.simbirsoft.corporatechat.domain.Room;
import ru.simbirsoft.corporatechat.domain.User;
import ru.simbirsoft.corporatechat.domain.dto.UserRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.UserResponseDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "rooms", source = "rooms", qualifiedByName = "roomToId")
    })
    UserResponseDto userToUserResponseDto(User entity);

    @Mappings({
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "rooms", source = "rooms", qualifiedByName = "idToRoom")

    })
    User userRequestDtoToUser(UserRequestDto dto);

    @Named("roomToId")
    public static Long roomToId(Room entity) {
        return entity.getId();
    }

    @Named("idToRoom")
    public static Room idToRoom(Long id) {
        return new Room(id);
    }
}
