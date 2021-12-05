package ru.simbirsoft.corporatechat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.simbirsoft.corporatechat.domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
