package ru.energomera.zabbixbot.command;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.controller.ZabbixRestService;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.zabbixapi.dto.HistoryResponseResult;
import ru.energomera.zabbixbot.zabbixapi.dto.RequestToZabbixHistory;
import ru.energomera.zabbixbot.zabbixapi.dto.ResponseFromZabbixHistory;

import java.util.ArrayList;
import java.util.List;

public class ProxyPingCommand implements Command, Chart{
    private final SendMessageService sendMessageService;
    private final ZabbixRestService zabbixRestService = new ZabbixRestService(new RestTemplateBuilder());

    private final String chartName = "Proxy server ICMP ping";
    private final String seriesName = "Proxy server";
    private final int proxyPingZabbixItemId = 33484;


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

        RequestToZabbixHistory proxyIcmpRequest = new RequestToZabbixHistory(proxyPingZabbixItemId, 20);
        ResponseFromZabbixHistory historyResponseFromZabbixHistory = zabbixRestService.createPostWithHistoryObject(proxyIcmpRequest);
        HistoryResponseResult[] historyResponseResult = historyResponseFromZabbixHistory.getResult();

        RequestToZabbixHistory proxyIcmpRequest2 = new RequestToZabbixHistory(33871, 20);
        ResponseFromZabbixHistory historyResponseFromZabbixHistory2 = zabbixRestService.createPostWithHistoryObject(proxyIcmpRequest2);
        HistoryResponseResult[] historyResponseResult2 = historyResponseFromZabbixHistory2.getResult();

        RequestToZabbixHistory proxyIcmpRequest3 = new RequestToZabbixHistory(35043, 20);
        ResponseFromZabbixHistory historyResponseFromZabbixHistory3 = zabbixRestService.createPostWithHistoryObject(proxyIcmpRequest3);
        HistoryResponseResult[] historyResponseResult3 = historyResponseFromZabbixHistory3.getResult();

            //временно заполним тут, потом в отдельные команды
        List<HistoryResponseResult[]> listOfHistoryResponseResults = new ArrayList<>();
        listOfHistoryResponseResults.add(historyResponseResult);
        listOfHistoryResponseResults.add(historyResponseResult2);
        listOfHistoryResponseResults.add(historyResponseResult3);

        String[] seriesNames = {"Proxy server", "yandex.ru", "google DNS"};


        sendMessageService.sendHistoryPicture(chatId, listOfHistoryResponseResults, chartName, seriesNames);
    }

    public void sendChart(String chatId, String subject, String message) {
        RequestToZabbixHistory proxyIcmpRequest = new RequestToZabbixHistory(proxyPingZabbixItemId, 20);
        ResponseFromZabbixHistory historyResponseFromZabbixHistory = zabbixRestService.createPostWithHistoryObject(proxyIcmpRequest);
        HistoryResponseResult[] historyResponseResult = historyResponseFromZabbixHistory.getResult();
//        String message = String.format("Колличество полученных объектов - %d", historyResponseResult.length);
//        sendMessageService.sendMessage(chatId, message);
        sendMessageService.sendHistoryPictureWithText(chatId, subject, message, historyResponseResult, chartName, seriesName);
    }


}
