package ru.simbirsoft.corporatechat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.corporatechat.domain.dto.MessageRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.MessageResponseDto;
import ru.simbirsoft.corporatechat.repository.MessageRepository;
import ru.simbirsoft.corporatechat.service.MessageService;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public MessageResponseDto findById(Long id) {
        return null;
    }

    @Override
    public MessageResponseDto editMessage(Long id, MessageRequestDto messageRequestDto) {
        return null;
    }

    @Override
    public MessageResponseDto createMessage(MessageRequestDto messageRequestDto) {
        return null;
    }

    @Override
    public boolean deleteMessageById(Long id) {
        return false;
    }
}
