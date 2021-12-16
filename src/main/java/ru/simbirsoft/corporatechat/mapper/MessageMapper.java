package ru.simbirsoft.corporatechat.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.simbirsoft.corporatechat.domain.Message;
import ru.simbirsoft.corporatechat.domain.dto.MessageRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.MessageResponseDto;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "authorId", source = "author.id"),
            @Mapping(target = "roomId", source = "room.id"),
            @Mapping(target = "deliveringTime", source = "deliveringTime"),
            @Mapping(target = "text", source = "text")

    })
    MessageResponseDto messageToMessageResponseDto(Message entity);

    @Mappings({
            @Mapping(target = "author.id", source = "authorId"),
            @Mapping(target = "room.id", source = "roomId"),
            @Mapping(target = "deliveringTime", source = "deliveringTime"),
            @Mapping(target = "text", source = "text")

    })
    Message messageRequestDtoToMessage(MessageRequestDto dto);
}
