package ru.simbirsoft.corporatechat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.simbirsoft.corporatechat.domain.enums.Role;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoom {

    @EmbeddedId
    UserRoomFK id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "room_id")
    Room room;

    @Enumerated(EnumType.STRING)
    Role role;

    boolean isBlocked;

    LocalDateTime startBlocking;

    int blockingDuration;
}
