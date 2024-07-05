package ru.dimov.universityschedule.repository;

import org.springframework.data.repository.CrudRepository;
import ru.dimov.universityschedule.model.TelegramUser;

import java.util.Optional;

public interface TelegramUserRepository extends CrudRepository<TelegramUser, Long> {
}
