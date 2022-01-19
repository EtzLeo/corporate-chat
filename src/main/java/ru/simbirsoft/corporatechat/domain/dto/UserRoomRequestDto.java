package ru.simbirsoft.corporatechat.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.simbirsoft.corporatechat.domain.enums.Role;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoomRequestDto {
    @NotNull
    long roomId;

    Role role;

    boolean blocked;

    LocalDateTime startBlocking;

    int blockingDuration;
}
