package ru.simbirsoft.corporatechat.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="users")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String password;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private Set<Message> messages;

    @OneToMany(mappedBy = "user")
    Set<UserRoom> userRooms;

//    @ManyToMany(fetch=FetchType.EAGER)
//    @JoinTable(name="user_room",
//            joinColumns = @JoinColumn(name="user_id"),
//            inverseJoinColumns = @JoinColumn(name="room_id")
//    )
//    private Set<Room> rooms;
}
