package ru.energomera.zabbixbot.command.privatechat;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.controller.ZabbixRestService;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.zabbixapi.dto.HistoryResponseResult;
import ru.energomera.zabbixbot.zabbixapi.dto.RequestToZabbixHistory;
import ru.energomera.zabbixbot.zabbixapi.dto.ResponseFromZabbixHistory;

public class ProxyPingCommand implements Command {
    private final SendMessageService sendMessageService;
    private final ZabbixRestService zabbixRestService;

    private final String chartName = "Proxy server ICMP ping";
    private final String seriesName = "Proxy server";
    private final int proxyPingZabbixItemId = 33484;
    private int requestCounter = 20;

    public ProxyPingCommand(SendMessageService sendMessageService, ZabbixRestService zabbixRestService) {
        this.sendMessageService = sendMessageService;
        this.zabbixRestService = zabbixRestService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        RequestToZabbixHistory proxyIcmpRequest = new RequestToZabbixHistory(proxyPingZabbixItemId, requestCounter);
        ResponseFromZabbixHistory historyResponseFromZabbixHistory = zabbixRestService.createPostWithHistoryObject(proxyIcmpRequest);
        HistoryResponseResult[] historyResponseResult = historyResponseFromZabbixHistory.getResult();

        sendMessageService.sendHistoryPicture(chatId, historyResponseResult, chartName, seriesName);
    }
}
