package ru.simbirsoft.corporatechat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.corporatechat.domain.dto.MessageRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.MessageResponseDto;
import ru.simbirsoft.corporatechat.service.MessageService;

import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/message")
public class MessageController {
    public final MessageService messageService;

    @GetMapping
    public MessageResponseDto getMessage(@RequestParam @NotNull Long id) {
        return messageService.findById(id);
    }

    @PostMapping
    public MessageResponseDto createMessage(@RequestBody @NotNull MessageRequestDto messageRequestDto) {
        return messageService.createMessage(messageRequestDto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_MODER', 'ROLE_ADMIN')")
    @DeleteMapping
    public void deleteMessage(@RequestParam @NotNull Long id) {
        messageService.deleteMessageById(id);
    }
}
