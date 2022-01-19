package ru.simbirsoft.corporatechat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Proxy(lazy=false)
@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRoomFK implements Serializable {

    @Column(name = "user_id")
    Long userId;

    @Column(name = "room_id")
    Long roomId;

}
