package ru.simbirsoft.corporatechat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.corporatechat.domain.Message;
import ru.simbirsoft.corporatechat.domain.dto.MessageRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.MessageResponseDto;
import ru.simbirsoft.corporatechat.exception.IllegalDataException;
import ru.simbirsoft.corporatechat.exception.ResourceNotFoundException;
import ru.simbirsoft.corporatechat.mapper.MessageMapper;
import ru.simbirsoft.corporatechat.repository.MessageRepository;
import ru.simbirsoft.corporatechat.service.MessageService;
import ru.simbirsoft.corporatechat.util.AuthUtil;

import java.time.LocalDateTime;
import java.util.Objects;
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
                .orElseThrow(() -> new ResourceNotFoundException("Message not found"));
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
        throw new ResourceNotFoundException("Message not found");
    }

    @Override
    public MessageResponseDto createMessage(MessageRequestDto messageRequestDto) {
        Long roomId =  AuthUtil.getCurrentUser().getRoomId();
        Long userId = AuthUtil.getCurrentUser().getId();

        if (messageRequestDto.getRoomId() != null &&
                !Objects.equals(messageRequestDto.getRoomId(), roomId)) {
            throw new IllegalDataException("Cannot send message to other room");
        }
        if (messageRequestDto.getAuthorId() != null &&
                !Objects.equals(messageRequestDto.getAuthorId(), userId)) {
            throw new IllegalDataException("Unable to send a message on behalf of another user");
        }

        messageRequestDto = new MessageRequestDto(userId, roomId, LocalDateTime.now(), messageRequestDto.getText());

        Message message = mapper.messageRequestDtoToMessage(messageRequestDto);
        if (Objects.equals(message.getRoom().getName(), "bot-room")) {
            throw new IllegalDataException("Unable to send a message to bot-room(only commands)");
        }

        messageRepository.save(message);
        return mapper.messageToMessageResponseDto(message);
    }

    @Override
    public void deleteMessageById(Long id) {
        Message message = messageRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Message not found"));

        messageRepository.delete(message);
    }
}
