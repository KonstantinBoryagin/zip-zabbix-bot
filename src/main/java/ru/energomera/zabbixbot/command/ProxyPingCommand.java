package ru.energomera.zabbixbot.command;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.controller.ZabbixRestService;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.zabbixapi.dto.RequestToZabbixHistory;
import ru.energomera.zabbixbot.zabbixapi.dto.ResponseFromZabbixHistory;
import ru.energomera.zabbixbot.zabbixapi.dto.HistoryResponseResult;

public class ProxyPingCommand implements Command, Chart{
    private final SendMessageService sendMessageService;
    private final ZabbixRestService zabbixRestService = new ZabbixRestService(new RestTemplateBuilder());

    private final String chartName = "Proxy server ICMP ping";
    private final String asixXName = "время";
    private final String asixYName = "ответ(мс)";
    private final String seriesName = "ICMP ping";


    public ProxyPingCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId;
        if(update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        } else if(update.hasChannelPost()){
            chatId = update.getChannelPost().getChatId().toString();
            System.out.println(chatId +  "hasChannelPost");
        } else {
            chatId = update.getMessage().getChatId().toString();
            System.out.println(chatId + "getMessage");
        }
//       String chatId = update.getCallbackQuery().getMessage().getChatId().toString();  //забираем у inline keyboard

        RequestToZabbixHistory proxyIcmpRequest = new RequestToZabbixHistory(33484, 10);
        ResponseFromZabbixHistory historyResponseFromZabbixHistory = zabbixRestService.createPostWithHistoryObject(proxyIcmpRequest);
        HistoryResponseResult[] historyResponseResult = historyResponseFromZabbixHistory.getResult();
//        String message = String.format("Колличество полученных объектов - %d", historyResponseResult.length);
//        sendMessageService.sendMessage(chatId, message);
        sendMessageService.sendHistoryPicture(chatId, historyResponseResult, chartName,
                asixXName, asixYName, seriesName);
    }

    public void sendChart(String chatId) {
        RequestToZabbixHistory proxyIcmpRequest = new RequestToZabbixHistory(33484, 10);
        ResponseFromZabbixHistory historyResponseFromZabbixHistory = zabbixRestService.createPostWithHistoryObject(proxyIcmpRequest);
        HistoryResponseResult[] historyResponseResult = historyResponseFromZabbixHistory.getResult();
//        String message = String.format("Колличество полученных объектов - %d", historyResponseResult.length);
//        sendMessageService.sendMessage(chatId, message);
        sendMessageService.sendHistoryPicture(chatId, historyResponseResult, chartName,
                asixXName, asixYName, seriesName);
    }


}
