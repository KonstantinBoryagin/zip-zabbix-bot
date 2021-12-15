package ru.energomera.zabbixbot.command.privatechat;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.controller.ZabbixRestController;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.model.zabbix.HistoryResponseResult;
import ru.energomera.zabbixbot.model.zabbix.RequestToZabbixHistory;
import ru.energomera.zabbixbot.model.zabbix.ResponseFromZabbixHistory;

public class SrvErpCpuCommand implements Command {
    private final SendMessageService sendMessageService;
    private final ZabbixRestController zabbixRestController;

    private final String chartName = "srv-erp 2 CPU utilization";
    private final String seriesName = "CPU utilization";
    private final int cpuUtilizationItemId = 32943;
    private final int requestCounter = 20;

    public SrvErpCpuCommand(SendMessageService sendMessageService, ZabbixRestController zabbixRestController) {
        this.sendMessageService = sendMessageService;
        this.zabbixRestController = zabbixRestController;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        RequestToZabbixHistory cpuUtilizationRequest = new RequestToZabbixHistory(cpuUtilizationItemId, requestCounter);
        ResponseFromZabbixHistory cpuHistoryResponse = zabbixRestController.createPostWithHistoryObject(cpuUtilizationRequest);
        HistoryResponseResult[] cpuHistoryResponseResult = cpuHistoryResponse.getResult();

        sendMessageService.sendCpuUtilizationChart(chatId, cpuHistoryResponseResult, chartName, seriesName);
    }
}
