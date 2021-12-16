package ru.energomera.zabbixbot.command.privatechat.chart;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.controller.ZabbixRestController;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.model.zabbix.HistoryResponseResult;
import ru.energomera.zabbixbot.model.zabbix.RequestToZabbixHistory;
import ru.energomera.zabbixbot.model.zabbix.ResponseFromZabbixHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс реализует {@link Command}
 * Формирует {@link ZabbixRestController} и отправляет график пинга интернет ресурсов
 */
public class InternetPingCommand implements Command {
    private final SendMessageService sendMessageService;
    private final ZabbixRestController zabbixRestController;

    private final String chartName = "Internet resources ping";
    private final int yandexPingZabbixItemId = 33871;
    private final int googleDnsPingZabbixItemId = 35043;
    private final int mailPingZabbixItemId = 33868;
    private final int requestCounter = 20;

    public InternetPingCommand(SendMessageService sendMessageService, ZabbixRestController zabbixRestController) {
        this.sendMessageService = sendMessageService;
        this.zabbixRestController = zabbixRestController;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        RequestToZabbixHistory proxyIcmpRequestYandex = new RequestToZabbixHistory(yandexPingZabbixItemId, requestCounter);
        ResponseFromZabbixHistory yandexHistoryResponseFromZabbixHistory = zabbixRestController.createPostWithHistoryObject(proxyIcmpRequestYandex);
        HistoryResponseResult[] historyResponseResultForYandex = yandexHistoryResponseFromZabbixHistory.getResult();

        RequestToZabbixHistory proxyIcmpRequestGoogle = new RequestToZabbixHistory(googleDnsPingZabbixItemId, requestCounter);
        ResponseFromZabbixHistory googleHistoryResponseFromZabbixHistory = zabbixRestController.createPostWithHistoryObject(proxyIcmpRequestGoogle);
        HistoryResponseResult[] historyResponseResultForGoogle = googleHistoryResponseFromZabbixHistory.getResult();

        RequestToZabbixHistory proxyIcmpRequestMail = new RequestToZabbixHistory(mailPingZabbixItemId, requestCounter);
        ResponseFromZabbixHistory mailHistoryResponseFromZabbixHistory = zabbixRestController.createPostWithHistoryObject(proxyIcmpRequestMail);
        HistoryResponseResult[] historyResponseResultForMail = mailHistoryResponseFromZabbixHistory.getResult();

        //временно заполним тут, потом в отдельные команды
        List<HistoryResponseResult[]> listOfHistoryResponseResults = new ArrayList<>();
        listOfHistoryResponseResults.add(historyResponseResultForYandex);
        listOfHistoryResponseResults.add(historyResponseResultForGoogle);
        listOfHistoryResponseResults.add(historyResponseResultForMail);

        String[] seriesNames = {"yandex.ru", "google DNS", "mail.ru"};


        sendMessageService.sendHistoryPictureForManyCharts(chatId, listOfHistoryResponseResults, chartName, seriesNames);
    }
}
