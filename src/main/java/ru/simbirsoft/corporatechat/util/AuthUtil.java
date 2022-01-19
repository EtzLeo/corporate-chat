package ru.simbirsoft.corporatechat.util;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.simbirsoft.corporatechat.config.jwt.JwtUser;
import ru.simbirsoft.corporatechat.domain.User;

import java.util.Objects;

public class AuthUtil {

    public static JwtUser getCurrentUser() {
        return (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static boolean resolveUser(String username) {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Objects.equals(jwtUser.getUsername(), username);
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
