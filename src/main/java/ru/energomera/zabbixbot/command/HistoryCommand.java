package ru.energomera.zabbixbot.command;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.service.ZabbixRestService;
import ru.energomera.zabbixbot.zabbixapi.dto.HistoryResponse;
import ru.energomera.zabbixbot.zabbixapi.dto.HistoryResult;
import ru.energomera.zabbixbot.zabbixapi.dto.PingResponse;
import ru.energomera.zabbixbot.zabbixapi.dto.PingResult;

public class HistoryCommand implements Command{
    private final SendMessageService sendMessageService;
    private final ZabbixRestService zabbixRestService = new ZabbixRestService(new RestTemplateBuilder());

    public HistoryCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        HistoryResponse historyResponse = zabbixRestService.createPostWithHistoryObject();
//        PingResult[] pingResult = pingResponse.getPingResult();
        HistoryResult[] historyResult = historyResponse.getHistoryResult();
        String message = String.format("Колличество полученных объектов - %d", historyResult.length);
        sendMessageService.sendMessage(chatId, message);
        sendMessageService.sendHistoryPicture(chatId, historyResult);
    }
}
