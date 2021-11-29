package ru.energomera.zabbixbot.command;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.service.ZabbixRestService;
import ru.energomera.zabbixbot.zabbixapi.dto.history.HistoryRequest;
import ru.energomera.zabbixbot.zabbixapi.dto.history.HistoryResponse;
import ru.energomera.zabbixbot.zabbixapi.dto.history.HistoryResult;

public class SrvErpCpuCommand implements Command{
    private final SendMessageService sendMessageService;
    private final ZabbixRestService zabbixRestService = new ZabbixRestService(new RestTemplateBuilder());

    public SrvErpCpuCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        HistoryRequest historyRequest = new HistoryRequest(32943, 20);
        HistoryResponse historyResponse = zabbixRestService.createPostWithHistoryObject(historyRequest);
        HistoryResult[] historyResponseResult = historyResponse.getResult();
        String message = String.format("Колличество полученных объектов - %d", historyResponseResult.length);
        sendMessageService.sendMessage(chatId, message);
        sendMessageService.sendHistoryPicture(chatId, historyResponseResult, "SRV-ERP 2: CPU Utilization",
                "время", "загрузка", "CPU utilization");
    }
}
