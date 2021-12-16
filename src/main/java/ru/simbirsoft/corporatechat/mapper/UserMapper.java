package ru.simbirsoft.corporatechat.mapper;

import org.mapstruct.*;
import ru.simbirsoft.corporatechat.domain.User;
import ru.simbirsoft.corporatechat.domain.dto.UserRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.UserResponseDto;

@Mapper(componentModel = "spring", uses = {RoomMapper.class})
public interface UserMapper {
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "password", source = "password"),
//            @Mapping(target = "rooms",source = "rooms")
    })
    UserResponseDto userToUserResponseDto(User entity);

    @Mappings({
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "password", source = "password"),
//            @Mapping(target = "rooms", qualifiedByName = "roomList")

    })
    User userRequestDtoToUser(UserRequestDto dto);
    }
