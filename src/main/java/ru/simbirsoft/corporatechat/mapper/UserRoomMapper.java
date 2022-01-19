package ru.simbirsoft.corporatechat.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.simbirsoft.corporatechat.domain.UserRoom;
import ru.simbirsoft.corporatechat.domain.dto.UserRoomRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.UserRoomResponseDto;

@Mapper(componentModel = "spring")
public interface UserRoomMapper {
    @Mappings({
            @Mapping(target = "userId", source = "id.userId"),
            @Mapping(target = "roomId", source = "id.roomId"),
            @Mapping(target = "role", source = "role"),
            @Mapping(target = "blocked", source = "blocked"),
            @Mapping(target = "startBlocking", source = "startBlocking"),
            @Mapping(target = "blockingDuration", source = "blockingDuration")
    })
    UserRoomResponseDto userRoomToUserRoomResponseDto(UserRoom entity);

    @Mappings({
            @Mapping(target = "id.roomId", source = "roomId"),
            @Mapping(target = "role", source = "role"),
            @Mapping(target = "blocked", source = "blocked"),
            @Mapping(target = "startBlocking", source = "startBlocking"),
            @Mapping(target = "blockingDuration", source = "blockingDuration")
    })
    UserRoom userRoomResponseDtoToUserRoom(UserRoomRequestDto dto);
}
