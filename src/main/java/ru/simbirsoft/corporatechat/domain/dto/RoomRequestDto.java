package ru.simbirsoft.corporatechat.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.corporatechat.domain.User;
import ru.simbirsoft.corporatechat.domain.enums.RoomType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequestDto {
    private String name;

    private User owner;

    private RoomType type;
}
