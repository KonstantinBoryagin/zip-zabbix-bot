package ru.energomera.zabbixbot.command.privatechat;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.controller.ZabbixRestController;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.model.zabbix.HistoryResponseResult;
import ru.energomera.zabbixbot.model.zabbix.RequestToZabbixHistory;
import ru.energomera.zabbixbot.model.zabbix.ResponseFromZabbixHistory;

public class ProxyPingCommand implements Command {
    private final SendMessageService sendMessageService;
    private final ZabbixRestController zabbixRestController;

    private final String chartName = "Proxy server ICMP ping";
    private final String seriesName = "Proxy server";
    private final int proxyPingZabbixItemId = 33484;
    private int requestCounter = 20;

    public ProxyPingCommand(SendMessageService sendMessageService, ZabbixRestController zabbixRestController) {
        this.sendMessageService = sendMessageService;
        this.zabbixRestController = zabbixRestController;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        RequestToZabbixHistory proxyIcmpRequest = new RequestToZabbixHistory(proxyPingZabbixItemId, requestCounter);
        ResponseFromZabbixHistory historyResponseFromZabbixHistory = zabbixRestController.createPostWithHistoryObject(proxyIcmpRequest);
        HistoryResponseResult[] historyResponseResult = historyResponseFromZabbixHistory.getResult();

        sendMessageService.sendHistoryPicture(chatId, historyResponseResult, chartName, seriesName);
    }
}
