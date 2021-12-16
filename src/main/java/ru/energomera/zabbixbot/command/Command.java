package ru.energomera.zabbixbot.command;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Интерфейс для всех команд бота
 */
public interface Command {

    void execute(Update update);

}
