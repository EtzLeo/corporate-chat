package ru.simbirsoft.corporatechat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.corporatechat.config.jwt.JwtUser;
import ru.simbirsoft.corporatechat.domain.Room;
import ru.simbirsoft.corporatechat.domain.User;
import ru.simbirsoft.corporatechat.domain.UserRoom;
import ru.simbirsoft.corporatechat.domain.UserRoomFK;
import ru.simbirsoft.corporatechat.domain.dto.*;
import ru.simbirsoft.corporatechat.domain.enums.Role;
import ru.simbirsoft.corporatechat.domain.enums.RoomType;
import ru.simbirsoft.corporatechat.exception.IllegalDataException;
import ru.simbirsoft.corporatechat.exception.ResourceNotFoundException;
import ru.simbirsoft.corporatechat.mapper.UserRoomMapper;
import ru.simbirsoft.corporatechat.repository.RoomRepository;
import ru.simbirsoft.corporatechat.repository.UserRepository;
import ru.simbirsoft.corporatechat.repository.UserRoomRepository;
import ru.simbirsoft.corporatechat.service.UserRoomService;
import ru.simbirsoft.corporatechat.util.AuthUtil;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserRoomServiceImpl implements UserRoomService {

    public final UserRoomRepository userRoomRepository;

    public final RoomRepository roomRepository;

    public final UserRepository userRepository;

    public final UserRoomMapper mapper;

    @Transactional
    @Override
    public UserRoomResponseDto findById(UserRoomFK id) {
        return userRoomRepository
                .findById(id)
                .map(mapper::userRoomToUserRoomResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("User in room not found"));
    }

    @Transactional
    @Override
    public UserRoomResponseDto getById(UserRoomFK id) {
        UserRoomResponseDto dto = userRoomRepository
                .findById(id)
                .map(mapper::userRoomToUserRoomResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("User in room not found"));

        if(!AuthUtil.getCurrentUser().getRoomId().equals(dto.getRoomId())) {
            throw new AccessDeniedException("User not in this room");
        }
        return dto;
    }

    @Transactional
    @Override
    public UserRoomResponseDto setBlock(UserRoomFK id, UserRoomRequestDto dto) {
        if (isBlocked()) {
            throw new AccessDeniedException("You are blocked");
        }

        UserRoom userRoom = userRoomRepository.findById(new UserRoomFK(id.getUserId(),id.getRoomId()))
                .orElseThrow(() -> new ResourceNotFoundException("Not found user in room"));

        if(AuthUtil.getCurrentUser().getUsername().equals(userRoom.getUser().getName())) {
            throw new AccessDeniedException("You can't block yourself");
        }

        if(Objects.equals(userRoom.getRoom().getOwner().getName(), userRoom.getUser().getName())) {
            throw new AccessDeniedException("You can't block owner of the room");
        }

        if(AuthUtil.getCurrentUser().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MODER"))
        && Objects.equals(userRoom.getRole(), Role.ROLE_ADMIN)) {
            throw new AccessDeniedException("You can't block admin of the room");
        }

        userRoom.setBlocked(dto.isBlocked());
        if (dto.isBlocked()) {
            userRoom.setStartBlocking(LocalDateTime.now());
            userRoom.setBlockingDuration(userRoom.getBlockingDuration() + dto.getBlockingDuration());
        }
        else {
            userRoom.setBlockingDuration(0);
        }

        userRoomRepository.save(userRoom);
        return mapper.userRoomToUserRoomResponseDto(userRoom);
    }

    @Transactional
    @Override
    public UserRoomResponseDto setRole(UserRoomFK id, Role role) {
        if (isBlocked()) {
            throw new AccessDeniedException("You are blocked");
        }

        UserRoom user = userRoomRepository.findById(new UserRoomFK(id.getUserId(),id.getRoomId()))
                .orElseThrow(() -> new ResourceNotFoundException("Not found user in room"));
        User owner = roomRepository.getById(id.getRoomId()).getOwner();

        if (role.equals(Role.ROLE_ADMIN) || user.getRole().equals(Role.ROLE_ADMIN)) {
            if(!AuthUtil.getCurrentUser().getUsername().equals(owner.getName()))
                throw new AccessDeniedException("You are not owner of room");
            else user.setRole(role);
        }
        else {
            if (role.equals(Role.ROLE_MODER)) {
                if (!AuthUtil.getCurrentUser().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
                    throw new AccessDeniedException("You are not admin of room");
                else user.setRole(role);
            }
            else if (role.equals(Role.ROLE_USER)) {
                user.setRole(role);
            }
        }

        userRoomRepository.save(user);
        return mapper.userRoomToUserRoomResponseDto(user);
    }

    @Transactional
    @Override
    public UserRoomResponseDto addUser(UserRoomFK id) {
        if (isBlocked()) {
            throw new AccessDeniedException("Blocked user cannot add user to the room");
        }

        Long userId = id.getUserId();
        Long roomId = id.getRoomId();

        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Current room not found"));

        if (Objects.equals(userId, null)){
            throw new ResourceNotFoundException("User not found");
        }

        if(room.getUsers()!=null) {
            room.getUsers().forEach(user -> {
                if (Objects.equals(user.getId(), userId)) {
                    throw new IllegalDataException("User already in the room");
                }
            });
        }

        if(room.getUsers().size() > 2) {
            room.setType(RoomType.PUBLIC);
        }

        UserRoom userRoom = new UserRoom(
                new UserRoomFK(userId, roomId),
                new User(userId),
                room,
                Role.ROLE_USER,
                false,
                LocalDateTime.now(),
                0
        );

        userRoomRepository.save(userRoom);
        return mapper.userRoomToUserRoomResponseDto(userRoom);
    }

    @Transactional
    @Override
    public UserRoomResponseDto registerUser(UserRoomFK id, Role role) {
        Long userId = id.getUserId();
        Long roomId = id.getRoomId();

        if(userId == null || roomId == null){
            throw new IllegalDataException("User or room data isn't correct");
        }

        UserRoom userRoom = new UserRoom(
                new UserRoomFK(userId, roomId),
                new User(userId),
                new Room(roomId),
                role,
                false,
                LocalDateTime.now(),
                0
        );
        userRoomRepository.save(userRoom);
        return mapper.userRoomToUserRoomResponseDto(userRoom);
    }

    @Transactional
    @Override
    public UserRoomResponseDto expelUser(UserRoomFK id) {
        if (isBlocked()) {
            throw new AccessDeniedException("You are blocked");
        }

        UserRoom userRoom = userRoomRepository.findById(new UserRoomFK(id.getUserId(),id.getRoomId()))
                .orElseThrow(() -> new ResourceNotFoundException("Not found user in room"));

        if(Objects.equals(userRoom.getRoom().getOwner().getName(), userRoom.getUser().getName())) {
            throw new AccessDeniedException("You can't expel owner of the room");
        }

        if(userRoom.getRoom().getUsers().size() < 3) {
            userRoom.getRoom().setType(RoomType.PRIVATE);
        }

        userRoomRepository.delete(userRoom);
        return mapper.userRoomToUserRoomResponseDto(userRoom);
    }

    @Transactional
    @Override
    public UserRoomResponseDto exitRoom(Long roomId) {
        UserRoom userRoom = userRoomRepository.findById(new UserRoomFK(AuthUtil.getCurrentUser().getId(),roomId))
                .orElseThrow(() -> new ResourceNotFoundException("Not found user in room"));
        if(Objects.equals(userRoom.getRoom().getOwner().getId(), userRoom.getUser().getId())) {
            throw new AccessDeniedException("Owner cannot leave the room");
        }
        userRoomRepository.delete(userRoom);
        return mapper.userRoomToUserRoomResponseDto(userRoom);
    }

    private boolean isBlocked() {
        JwtUser user = AuthUtil.getCurrentUser();
        UserRoom currentUser = userRoomRepository.findById(new UserRoomFK(user.getId(), user.getRoomId()))
                .orElseThrow(() -> new ResourceNotFoundException("User in room not found"));
        if(currentUser.isBlocked() && currentUser.getStartBlocking().plusMinutes(currentUser.getBlockingDuration()).isBefore(LocalDateTime.now())) {
            currentUser.setBlocked(false);
            currentUser.setBlockingDuration(0);
            userRoomRepository.save(currentUser);
            return false;
        }
        return !AuthUtil.getCurrentUser().isAccountNonLocked();
    }

}
