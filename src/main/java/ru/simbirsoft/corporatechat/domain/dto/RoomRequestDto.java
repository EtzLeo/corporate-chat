package ru.simbirsoft.corporatechat.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.corporatechat.domain.enums.RoomType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequestDto {
    @NotNull
    private String name;
    
    private long ownerId;

    private RoomType type;

    @NotEmpty
    private Set<Long> users;

}
