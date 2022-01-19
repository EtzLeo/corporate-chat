package ru.simbirsoft.corporatechat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Proxy;
import ru.simbirsoft.corporatechat.domain.enums.Role;

import javax.persistence.*;
import java.time.LocalDateTime;

@Proxy(lazy=false)
@Entity
@Table(name = "user_room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoom {

    @EmbeddedId
    private UserRoomFK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @MapsId("roomId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean blocked;

    private LocalDateTime startBlocking;

    private int blockingDuration;
}
