package ru.simbirsoft.corporatechat.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseDto {
    private Long id;

    private Long authorId;

    private Long roomId;

    private LocalDateTime deliveringTime;

    private String text;
}
