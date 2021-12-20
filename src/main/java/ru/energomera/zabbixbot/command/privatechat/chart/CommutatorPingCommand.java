package ru.energomera.zabbixbot.command.privatechat.chart;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.controller.ZabbixRestController;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.model.zabbix.HistoryResponseResult;
import ru.energomera.zabbixbot.model.zabbix.RequestToZabbixHistory;
import ru.energomera.zabbixbot.model.zabbix.ResponseFromZabbixHistory;

/**
 * Класс реализует {@link Command}
 * Формирует {@link ZabbixRestController} и отправляет график ping сервера
 */
public class CommutatorPingCommand implements Command {
    private final SendMessageService sendMessageService;
    private final ZabbixRestController zabbixRestController;

    private final String chartName = "Серверная(оптика) ICMP ping";
    private final String seriesName = "Серверная";
    private final int proxyPingZabbixItemId = 33805;
    private int requestCounter = 20;

    public CommutatorPingCommand(SendMessageService sendMessageService, ZabbixRestController zabbixRestController) {
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

