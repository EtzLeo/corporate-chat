package ru.simbirsoft.corporatechat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRoomFK implements Serializable {

    @Column(name = "user_id")
    long userId;

    @Column(name = "room_id")
    long roomId;
}
