package ru.simbirsoft.corporatechat.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.simbirsoft.corporatechat.domain.User;
import ru.simbirsoft.corporatechat.domain.UserRoom;
import ru.simbirsoft.corporatechat.domain.UserRoomFK;
import ru.simbirsoft.corporatechat.exception.ResourceNotFoundException;
import ru.simbirsoft.corporatechat.repository.UserRepository;
import ru.simbirsoft.corporatechat.repository.UserRoomRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoomRepository userRoomRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with username: " + username + " not found"));

        UserRoom userRoom = userRoomRepository.getById(new UserRoomFK(user.getId(),getBotRoomForUser(user)));

        return new JwtUser(
                user.getId(), user.getName(), user.getPassword(), getBotRoomForUser(user), userRoom.getRole(), false
        );
    }

    public UserDetails loadUserByUsernameAndRoomId(String username, Long roomId) throws UsernameNotFoundException {
        User user = userRepository
                .findByName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with username: " + username + " not found"));

        UserRoom userRoom = userRoomRepository.getById(new UserRoomFK(user.getId(),roomId));

        return new JwtUser(
                user.getId(), user.getName(), user.getPassword(), roomId, userRoom.getRole(), false
        );
    }

    public static Long getBotRoomForUser(User user) {

        final Long[] roomId = new Long[1];
        user.getRooms().forEach(room -> {
            if (Objects.equals(room.getOwner().getName(), "BOT")) {
                roomId[0] = room.getId();
            }
        });

        return roomId[0];
    }
}
