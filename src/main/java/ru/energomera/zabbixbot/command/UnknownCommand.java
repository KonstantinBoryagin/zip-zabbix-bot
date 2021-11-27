package ru.energomera.zabbixbot.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.service.SendMessageService;

public class UnknownCommand implements Command{
    private final SendMessageService sendMessageService;

    public static final String UNKNOWN_MESSAGE = "Не понимаю тебя";

    public UnknownCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        sendMessageService.sendMessage(chatId, UNKNOWN_MESSAGE);
    }
}
