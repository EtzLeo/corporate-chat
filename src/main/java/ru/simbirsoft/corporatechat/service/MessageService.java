package ru.simbirsoft.corporatechat.service;

import ru.simbirsoft.corporatechat.domain.dto.MessageRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.MessageResponseDto;

public interface MessageService {

    MessageResponseDto findById(Long id);
    MessageResponseDto editMessage(Long id, MessageRequestDto messageRequestDto);
    MessageResponseDto createMessage(MessageRequestDto messageRequestDto);
    void deleteMessageById(Long id);
}
