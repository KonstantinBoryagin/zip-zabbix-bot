package ru.energomera.zabbixbot.command;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.service.ZabbixRestService;
import ru.energomera.zabbixbot.zabbixapi.dto.PingResponse;
import ru.energomera.zabbixbot.zabbixapi.dto.UserResponse;

public class PingCommand implements Command{
    private final SendMessageService sendMessageService;

    public PingCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();


        ZabbixRestService zabbixRestService = new ZabbixRestService(new RestTemplateBuilder());

        PingResponse[] pingResponse = zabbixRestService.createPostWithObjects();
        String message = String.format("Колличество полученных объектов - %d", pingResponse.length);
        sendMessageService.sendMessage(chatId, message);
    }
}