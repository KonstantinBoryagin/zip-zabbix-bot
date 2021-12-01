package ru.energomera.zabbixbot.command;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.controller.ZabbixRestService;
import ru.energomera.zabbixbot.zabbixapi.dto.ping.PingResponse;
import ru.energomera.zabbixbot.zabbixapi.dto.ping.PingResult;

public class PingCommand implements Command{
    private final SendMessageService sendMessageService;
    private final ZabbixRestService zabbixRestService = new ZabbixRestService(new RestTemplateBuilder());

    public PingCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();


//        ZabbixRestService zabbixRestService = new ZabbixRestService(new RestTemplateBuilder());

        PingResponse pingResponse = zabbixRestService.createPostWithObjects();
        PingResult[] pingResult = pingResponse.getResult();
        String message = String.format("Колличество полученных объектов - %d", pingResult.length);
//        sendMessageService.sendMessage(chatId, message);
        sendMessageService.sendPingPicture(chatId, pingResult);
    }
}
