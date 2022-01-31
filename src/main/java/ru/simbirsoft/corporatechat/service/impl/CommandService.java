package ru.simbirsoft.corporatechat.service.impl;

import com.google.api.services.youtube.model.Video;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simbirsoft.corporatechat.config.jwt.JwtUser;
import ru.simbirsoft.corporatechat.config.jwt.JwtUserDto;
import ru.simbirsoft.corporatechat.domain.UserRoomFK;
import ru.simbirsoft.corporatechat.domain.dto.RoomRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.UserRoomRequestDto;
import ru.simbirsoft.corporatechat.domain.dto.YoutubeDto;
import ru.simbirsoft.corporatechat.domain.enums.Role;
import ru.simbirsoft.corporatechat.domain.enums.RoomType;
import ru.simbirsoft.corporatechat.exception.IllegalDataException;
import ru.simbirsoft.corporatechat.service.RoomService;
import ru.simbirsoft.corporatechat.service.UserRoomService;
import ru.simbirsoft.corporatechat.service.UserService;
import ru.simbirsoft.corporatechat.util.AuthUtil;
import ru.simbirsoft.corporatechat.util.SearchVideo;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandService {

    private final UserRoomService userRoomService;
    private final UserService userService;
    private final RoomService roomService;
    private final AuthService authService;
    private final SearchVideo searchVideo;

    public String recognizeCommandType(String command) throws IOException {
        if (roomService.findById(AuthUtil.getCurrentUser().getRoomId()).getOwnerId() != 1) {
            return "You must be in the room with the bot";
        }
        if (!command.matches("^//.+")) {
            throw new IllegalDataException("Invalid command");
        }
        else {
            command = command.substring(2);
            if (command.matches("^room .*")) {
                return executeRoomCommand(command.substring(5));
            }
            if (command.matches("^user .*")) {
                return executeUserCommand(command.substring(5));
            }
            if (command.matches("^yBot .*")) {
                return executeYoutubeCommand(command.substring(5));
            }
        }
        return "Cannot execute command";
    }

    private String executeYoutubeCommand(String command) throws IOException {
        if (command.matches("^find -k \\w+/(\\w+ ?)+( -v)?( -l)?")) {
            command = command.substring(8);
            String[] params = command.split("/");
            String channelName = params[0];
            String videoName = params[1];
            boolean views = false;
            boolean likes = false;
            if (videoName.matches("(\\w+ ?)+ -v -l")) {
                videoName = videoName.substring(0,videoName.length()-6);
                views = true;
                likes = true;
            } else if (videoName.matches("(\\w+ ?)+ -v")) {
                videoName = videoName.substring(0,videoName.length()-3);
                views = true;
            }
            else if (videoName.matches("(\\w+ ?)+ -l")) {
                videoName = videoName.substring(0,videoName.length()-3);
                likes = true;
            }

            List<YoutubeDto> srcs = new ArrayList<>();
            for (Video video : searchVideo.getVideos(videoName, searchVideo.getChannelId(channelName), 10L, false)) {
               YoutubeDto dto = new YoutubeDto();
               dto.setSrc("https://www.youtube.com/watch?v=" + video.getId() + "&ab_channel="+video.getSnippet().getChannelTitle());
               if (views) {
                   dto.setViews(video.getStatistics().getViewCount().toString());
               }
               if (likes) {
                   dto.setLikes(video.getStatistics().getLikeCount().toString());
               }
               srcs.add(dto);
            }
            return srcs.toString();
        }
        if (command.matches("^videoCommentRandom \\w+/(\\w+ ?)+")) {
            command = command.substring(19);
            String[] params = command.split("/");
            String channelName = params[0];
            String videoName = params[1];
            Map<String, String> comments = searchVideo.getComments(searchVideo
                            .getVideos(videoName, searchVideo.getChannelId(channelName), 10L, false)
                            .get(0),100L);
            Random random = new Random();
            Object author = comments.keySet().toArray()[random.nextInt(comments.size())];
            String comment = comments.get(author);
            return author + "\n" + comment;
        }
        return "Cannot execute command";
    }

    private String executeUserCommand(String command) {
        if (command.matches("^moderator \\w+ (-n)|(-d)")) {
            String[] params = command.split(" ");
            Long userId = userService.findByName(params[0]).getId();
            Long roomId = AuthUtil.getCurrentUser().getRoomId();

            if (Objects.equals(params[1], "-n")) {
                userRoomService.setRole(new UserRoomFK(userId, roomId), Role.ROLE_MODER);
                return "User is now a Moderator";
            }
            if (Objects.equals(params[1], "-d")) {
                userRoomService.setRole(new UserRoomFK(userId, roomId), Role.ROLE_USER);
                return "User demoted";
            }
        }
        return "Cannot execute command";
    }

    private String executeRoomCommand(String command){
        if (command.matches("^create \\w+ (\\d+,?)+ (-c)?")) {
            String[] params = command.split(" ");
            Set<Long> users = Arrays.stream(params[2].split(",")).map(Long::valueOf).collect(Collectors.toSet());
            RoomRequestDto dto = new RoomRequestDto(
                    params[1],
                    0,
                    RoomType.PRIVATE,
                    users
            );
            roomService.createRoom(dto);
            return "Room successfully created";
        }
        if (command.matches("^remove \\w+")) {
            String[] params = command.split(" ");
            if (!userRoomService
                    .findById(new UserRoomFK(AuthUtil.getCurrentUser().getId(), roomService.findByName(params[1])
                            .getId()))
                    .getRole()
                    .equals(Role.ROLE_ADMIN))
            {
                return "Cannot rename room";
            }

            roomService.deleteRoomById(roomService.findByName(params[1]).getId());
            return "Room successfully deleted";
        }
        if (command.matches("^rename \\w+ \\w+")) {
            String[] params = command.split(" ");
            if (!userRoomService
                    .findById(new UserRoomFK(AuthUtil.getCurrentUser().getId(), roomService.findByName(params[1])
                            .getId()))
                    .getRole()
                    .equals(Role.ROLE_ADMIN))
            {
                return "Cannot rename room";
            }

            roomService.renameRoom(roomService.findByName(params[1]).getId(), params[2]);
            return "Room successfully renamed";
        }
        if (command.matches("^connect \\w+ (-l \\w+)?")) {
            return connectRoom(command);
        }
        if (command.matches("^disconnect \\w+ (-l \\w+ (-m \\d+)?)?")) {
            return disconnectRoom(command);
        }
        return "Cannot execute command";
    }

    private String disconnectRoom(String command) {
        String[] params = command.split(" ");

        Long roomId = roomService.findByName(params[1]).getId();
        long userId = 0;
        if (params.length ==  2) {
            userRoomService.exitRoom(roomId);
            return "You left the room";
        }
        userId = userService.findByName(params[3]).getId();
        if (params.length == 4) {
            userRoomService.expelUser(new UserRoomFK(userId, roomId));
            return "You have expel the user from the room";
        }
        if (params.length == 6) {
            int duration = Integer.parseInt(params[5]);
            userRoomService.setBlock(new UserRoomFK(userId, roomId),new UserRoomRequestDto(
                    roomId,
                    Role.ROLE_USER,
                    true,
                    null,
                    duration
            ));
            return "You have blocked the user from the room";
        }

        return "User cannot disconnect";
    }

    private String connectRoom(String command) {
        String[] params = command.split(" ");

        Long roomId = roomService.findByName(params[1]).getId();
        long userId;

        if (params.length ==  2) {
            JwtUser user = AuthUtil.getCurrentUser();
            authService.login(roomId, new JwtUserDto(
                    user.getUsername(),
                    user.getPassword()
            ));
            return "User successfully connected";
        }
        else if (params.length == 4){
            userId = userService.findByName(params[3]).getId();
            userRoomService.addUser(new UserRoomFK(userId, roomId));
            return "User successfully added";
        }
        return "User not connected";
    }
}
