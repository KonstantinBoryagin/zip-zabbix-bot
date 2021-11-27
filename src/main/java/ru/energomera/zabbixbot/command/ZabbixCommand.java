package ru.energomera.zabbixbot.command;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.service.ZabbixRestService;
import ru.energomera.zabbixbot.zabbixapi.UserResponse;

public class ZabbixCommand implements Command{
    private final SendMessageService sendMessageService;

//    public static final String START_MESSAGE = "Привет, это бот для мониторинга Zabbix системы";

    public ZabbixCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        ZabbixRestService zabbixRestService = new ZabbixRestService(new RestTemplateBuilder());

        UserResponse postWithObject = zabbixRestService.createPostWithObject();
        sendMessageService.sendMessage(chatId, postWithObject.toString());
    }
}
