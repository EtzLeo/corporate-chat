package ru.simbirsoft.corporatechat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.simbirsoft.corporatechat.domain.UserRoom;
import ru.simbirsoft.corporatechat.domain.UserRoomFK;

@Repository
public interface UserRoomRepository extends JpaRepository<UserRoom, UserRoomFK> {
}
