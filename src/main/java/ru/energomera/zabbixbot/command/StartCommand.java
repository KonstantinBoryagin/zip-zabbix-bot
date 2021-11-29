package ru.energomera.zabbixbot.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.sticker.Icon;

public class StartCommand implements Command{

    private final SendMessageService sendMessageService;

    public static final String START_MESSAGE = "Привет, это %s для мониторинга Zabbix системы. " +
            "Для того что бы получать уведомления, оформите заявку в ОИТ " +
            "<b>обязательно</b> приложив Ваш User Id - <b>%s</b>!";

    public StartCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String message = String.format(START_MESSAGE, Icon.ROBOT_FACE.get(), chatId);
        sendMessageService.sendMessage(chatId, message);
    }
}
