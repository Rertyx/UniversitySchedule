package ru.dimov.universityschedule.telegram;

public class TelegramUnregisteredException extends RuntimeException{
    public TelegramUnregisteredException(String message) {
        super(message);
    }
}
