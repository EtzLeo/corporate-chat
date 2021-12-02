package ru.simbirsoft.corporatechat.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="message")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
//            (fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne
//            (fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Room room;

    private LocalDateTime deliveringTime;

    private String text;
}
