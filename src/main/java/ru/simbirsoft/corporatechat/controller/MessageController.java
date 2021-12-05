package ru.simbirsoft.corporatechat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.simbirsoft.corporatechat.domain.dto.MessageRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.MessageResponseDto;
import ru.simbirsoft.corporatechat.service.MessageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/message")
public class MessageController {
    public final MessageService messageService;

    @GetMapping
    public MessageResponseDto getMessage(@RequestParam Long id) {
        if (id != null) {
            return messageService.findById(id);
        }
        return null;
    }

    @PostMapping
    public MessageResponseDto createMessage(@RequestBody MessageRequestDto messageRequestDto) {
        if (messageRequestDto != null) {
            return messageService.createMessage(messageRequestDto);
        }
        return null;
    }

    @PatchMapping
    public MessageResponseDto updateMessage(@RequestParam Long id, @RequestBody MessageRequestDto messageRequestDto) {
        if (messageRequestDto != null) {
            return messageService.editMessage(id, messageRequestDto);
        }
        return null;
    }

    @DeleteMapping
    public boolean deleteMessage(@RequestParam Long id) {
        if (id != null) {
            return messageService.deleteMessageById(id);
        }
        return false;
    }
}
