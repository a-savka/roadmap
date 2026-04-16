package ru.savka.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.savka.demo.entity.AppMessage;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppMessageRepository extends JpaRepository<AppMessage, Long> {
    List<AppMessage> findByCategory(String category);
    Optional<AppMessage> findByMessageKey(String messageKey);
}
