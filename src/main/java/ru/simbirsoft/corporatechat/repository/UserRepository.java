package ru.simbirsoft.corporatechat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.simbirsoft.corporatechat.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
