package ru.energomera.zabbixbot.command.privatechat;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.controller.ZabbixRestService;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.zabbixapi.dto.HistoryResponseResult;
import ru.energomera.zabbixbot.zabbixapi.dto.RequestToZabbixHistory;
import ru.energomera.zabbixbot.zabbixapi.dto.ResponseFromZabbixHistory;

public class SrvErpCpuCommand implements Command {
    private final SendMessageService sendMessageService;
    private final ZabbixRestService zabbixRestService;

    private final String chartName = "srv-erp 2 CPU utilization";
    private final String seriesName = "CPU utilization";
    private final int cpuUtilizationItemId = 32943;
    private final int requestCounter = 20;

    public SrvErpCpuCommand(SendMessageService sendMessageService, ZabbixRestService zabbixRestService) {
        this.sendMessageService = sendMessageService;
        this.zabbixRestService = zabbixRestService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        RequestToZabbixHistory cpuUtilizationRequest = new RequestToZabbixHistory(cpuUtilizationItemId, requestCounter);
        ResponseFromZabbixHistory cpuHistoryResponse = zabbixRestService.createPostWithHistoryObject(cpuUtilizationRequest);
        HistoryResponseResult[] cpuHistoryResponseResult = cpuHistoryResponse.getResult();

        sendMessageService.sendCpuUtilization(chatId, cpuHistoryResponseResult, chartName, seriesName);
    }
}
