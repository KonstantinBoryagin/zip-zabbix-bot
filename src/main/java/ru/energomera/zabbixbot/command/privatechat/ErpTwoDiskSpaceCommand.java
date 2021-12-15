package ru.energomera.zabbixbot.command.privatechat;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.controller.ZabbixRestController;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.model.zabbix.HistoryResponseResult;
import ru.energomera.zabbixbot.model.zabbix.RequestToZabbixHistory;
import ru.energomera.zabbixbot.model.zabbix.ResponseFromZabbixHistory;

public class ErpTwoDiskSpaceCommand implements Command {
    private final SendMessageService sendMessageService;
    private final ZabbixRestController zabbixRestController;

    private final String chartName = "srv-erp 2 used F: disk space";
    private final String seriesName = "used disk F: space";
    private final int proxyPingZabbixItemId = 33058;
    private int requestCounter = 1;

    public ErpTwoDiskSpaceCommand(SendMessageService sendMessageService, ZabbixRestController zabbixRestController) {
        this.sendMessageService = sendMessageService;
        this.zabbixRestController = zabbixRestController;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        RequestToZabbixHistory proxyIcmpRequest = new RequestToZabbixHistory(proxyPingZabbixItemId, requestCounter);
        ResponseFromZabbixHistory historyResponseFromZabbixHistory = zabbixRestController.createPostWithHistoryObject(proxyIcmpRequest);
        HistoryResponseResult[] historyResponseResult = historyResponseFromZabbixHistory.getResult();

        sendMessageService.sendPiePicture(chatId, historyResponseResult, chartName, seriesName);
    }
}
