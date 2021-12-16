package ru.simbirsoft.corporatechat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.corporatechat.domain.Message;
import ru.simbirsoft.corporatechat.domain.dto.MessageRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.MessageResponseDto;
import ru.simbirsoft.corporatechat.exception.MessageNotFoundException;
import ru.simbirsoft.corporatechat.mapper.MessageMapper;
import ru.simbirsoft.corporatechat.repository.MessageRepository;
import ru.simbirsoft.corporatechat.service.MessageService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper mapper;

    @Override
    public MessageResponseDto findById(Long id) {
        return messageRepository
                .findById(id)
                .map(mapper::messageToMessageResponseDto)
                .orElseThrow(() -> new MessageNotFoundException("message not found: " + id));
    }

    @Override
    public MessageResponseDto editMessage(Long id, MessageRequestDto messageRequestDto) {
        Optional<Message> messageOptional = messageRepository.findById(id);
        if (messageOptional.isPresent()){
            Message message = mapper.messageRequestDtoToMessage(messageRequestDto);
            message.setId(id);
            messageRepository.save(message);
            return mapper.messageToMessageResponseDto(message);
        }
        throw new MessageNotFoundException("message not found: " + id);
    }

    @Override
    public MessageResponseDto createMessage(MessageRequestDto messageRequestDto) {
        Message message = messageRepository.save(mapper.messageRequestDtoToMessage(messageRequestDto));
        return mapper.messageToMessageResponseDto(message);
    }

    @Override
    public void deleteMessageById(Long id) {
        Message message = messageRepository
                .findById(id)
                .orElseThrow(
                        () -> new MessageNotFoundException("message not found: " + id));

        messageRepository.delete(message);
    }
}
