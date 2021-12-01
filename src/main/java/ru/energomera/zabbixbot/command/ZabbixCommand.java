package ru.energomera.zabbixbot.command;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.controller.ZabbixRestService;
import ru.energomera.zabbixbot.zabbixapi.dto.UserResponse;

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

        int id = (int) (Math.random() * 10);
        UserResponse response = zabbixRestService.createPostWithObject(id);
        String message = String.format("Ваш id - %d. \nВаш токен - %s", response.getId(), response.getResult());
//        sendMessageService.sendMessage(chatId, message);
    }
}
