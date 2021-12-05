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
    private Long id;

    private String name;

    private String password;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private Set<Message> messages;

    @OneToMany(mappedBy = "user")
    Set<UserRoom> userRooms;
}
