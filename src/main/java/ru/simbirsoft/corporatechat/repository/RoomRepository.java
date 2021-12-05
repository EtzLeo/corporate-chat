package ru.simbirsoft.corporatechat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.simbirsoft.corporatechat.domain.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
