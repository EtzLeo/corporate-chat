package ru.simbirsoft.corporatechat.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.corporatechat.domain.enums.RoomType;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequestDto {
    private String name;

    private long ownerId;

    private RoomType type;

//    private Set<Long> users;

}
