package ru.energomera.zabbixbot.command;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.controller.ZabbixRestService;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.zabbixapi.dto.HistoryResponseResult;
import ru.energomera.zabbixbot.zabbixapi.dto.RequestToZabbixHistory;
import ru.energomera.zabbixbot.zabbixapi.dto.ResponseFromZabbixHistory;

public class YandexPingCommand implements Command, Chart{
    private final SendMessageService sendMessageService;
    private final ZabbixRestService zabbixRestService = new ZabbixRestService(new RestTemplateBuilder());

    private final String chartName = "ya.ru ICMP ping response";
    private final String seriesName = "Yandex";
    private final int yandexPingZabbixItemId = 33871;


    public YandexPingCommand(SendMessageService sendMessageService) {
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

        RequestToZabbixHistory proxyIcmpRequest = new RequestToZabbixHistory(yandexPingZabbixItemId, 20);
        ResponseFromZabbixHistory historyResponseFromZabbixHistory = zabbixRestService.createPostWithHistoryObject(proxyIcmpRequest);
        HistoryResponseResult[] historyResponseResult = historyResponseFromZabbixHistory.getResult();

//        sendMessageService.sendHistoryPicture(chatId, historyResponseResult, chartName, seriesName);
    }

    public void sendChart(String chatId, String subject, String message) {
        RequestToZabbixHistory yandexIcmpRequest = new RequestToZabbixHistory(yandexPingZabbixItemId, 20);
        ResponseFromZabbixHistory historyResponseFromZabbixHistory = zabbixRestService.createPostWithHistoryObject(yandexIcmpRequest);
        HistoryResponseResult[] historyResponseResult = historyResponseFromZabbixHistory.getResult();

        sendMessageService.sendHistoryPictureWithText(chatId, subject, message, historyResponseResult, chartName, seriesName);
    }


}
