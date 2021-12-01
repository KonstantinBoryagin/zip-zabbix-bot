package ru.energomera.zabbixbot.command;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.controller.ZabbixRestService;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.zabbixapi.dto.RequestToZabbixHistory;
import ru.energomera.zabbixbot.zabbixapi.dto.ResponseFromZabbixHistory;
import ru.energomera.zabbixbot.zabbixapi.dto.HistoryResponseResult;

public class SrvErpCpuCommand implements Command{
    private final SendMessageService sendMessageService;
    private final ZabbixRestService zabbixRestService = new ZabbixRestService(new RestTemplateBuilder());

    public SrvErpCpuCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
//        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();

        RequestToZabbixHistory proxyIcmpRequest = new RequestToZabbixHistory(32943, 20);
        ResponseFromZabbixHistory historyResponse = zabbixRestService.createPostWithHistoryObject(proxyIcmpRequest);
        HistoryResponseResult[] historyResponseResult = historyResponse.getResult();
        String message = String.format("Колличество полученных объектов - %d", historyResponseResult.length);
//        sendMessageService.sendMessage(chatId, message);
//        sendMessageService.sendHistoryPicture(chatId, historyResponseResult, "SRV-ERP 2: CPU Utilization",
//                "время", "загрузка", "CPU utilization", ChartCommand.replyChartOptions());
    }
}
