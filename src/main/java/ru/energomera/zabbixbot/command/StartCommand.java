package ru.energomera.zabbixbot.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.service.SendMessageService;

public class StartCommand implements Command{

    private final SendMessageService sendMessageService;

    public static final String START_MESSAGE = "Привет, это бот для мониторинга Zabbix системы";

    public StartCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        sendMessageService.sendMessage(chatId, START_MESSAGE);
    }
}
