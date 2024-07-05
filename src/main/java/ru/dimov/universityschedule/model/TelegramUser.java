package ru.dimov.universityschedule.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "telegram_user")
public class TelegramUser {
    @Id
    @Column(name = "chat_id")
    private Long chatId;
    @Column(name = "group_number")
    private Integer groupNumber;
}
