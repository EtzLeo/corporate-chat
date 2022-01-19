package ru.simbirsoft.corporatechat.domain;

import lombok.*;
import ru.simbirsoft.corporatechat.domain.enums.RoomType;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "room")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    private String name;

    @OneToOne
    @JoinColumn(name="owner_id")
    private User owner;

    @Enumerated(EnumType.STRING)
    private RoomType type;

    @ManyToMany(mappedBy = "rooms")
    private Set<User> users;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private Set<Message> messages;

    @OneToMany(mappedBy = "room")
    private Set<UserRoom> userRooms;

    public Room(Long id) {
        this.id = id;
    }
}
