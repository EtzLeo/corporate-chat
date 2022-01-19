package ru.simbirsoft.corporatechat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.corporatechat.config.jwt.JwtTokenProvider;
import ru.simbirsoft.corporatechat.config.jwt.JwtUserDto;
import ru.simbirsoft.corporatechat.domain.User;
import ru.simbirsoft.corporatechat.domain.UserRoomFK;
import ru.simbirsoft.corporatechat.domain.dto.*;
import ru.simbirsoft.corporatechat.domain.enums.Role;
import ru.simbirsoft.corporatechat.domain.enums.RoomType;
import ru.simbirsoft.corporatechat.exception.ResourceNotFoundException;
import ru.simbirsoft.corporatechat.repository.UserRepository;
import ru.simbirsoft.corporatechat.service.RoomService;
import ru.simbirsoft.corporatechat.service.UserRoomService;
import ru.simbirsoft.corporatechat.service.UserService;
import ru.simbirsoft.corporatechat.util.AuthUtil;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRoomService userRoomService;

    private final UserRepository userRepository;

    private final UserService userService;

    private final RoomService roomService;

    @Transactional
    public Map<Object, Object> login(Long id, JwtUserDto dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getName(), dto.getPassword())
        );

        Long roomId = id;

        User user = userRepository
                .findByName(dto.getName())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User with username: " + dto.getName() + " not found"));

        if(roomId == null) {
            roomId = AuthUtil.getBotRoomForUser(user);
        }

        UserRoomResponseDto userRoom = userRoomService.findById(new UserRoomFK(user.getId(), roomId));
        String token = jwtTokenProvider.createToken(dto.getName(), List.of(userRoom.getRole()), roomId);

        Map<Object, Object> response = new HashMap<>();
        response.put("room",roomId);
        response.put("name", dto.getName());
        response.put("token", token);

        return response;
    }

    @Transactional
    public void register( JwtUserDto dto){
        long botId = 1;

        UserResponseDto user = userService.register(new UserRequestDto(
                dto.getName(),
                dto.getPassword(),
                null
        ));
        RoomResponseDto room = roomService.createRoomOnRegistration(
                new RoomRequestDto("bot-room", botId, RoomType.PRIVATE, Set.of(user.getId()))
        );
        userRoomService.register(new UserRoomFK(botId, room.getId()), Role.ROLE_ADMIN);
        userRoomService.register(new UserRoomFK(user.getId(), room.getId()), Role.ROLE_USER);
    }
}
