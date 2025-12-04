package ru.savka.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.savka.demo.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
}
