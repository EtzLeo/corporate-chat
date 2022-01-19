package ru.simbirsoft.corporatechat.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestDto {
    @NotNull
    private Long authorId;

    @NotNull
    private Long roomId;

    private LocalDateTime deliveringTime;

    @NotBlank
    private String text;
}
