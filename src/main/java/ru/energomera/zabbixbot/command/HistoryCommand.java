package ru.energomera.zabbixbot.command;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.controller.ZabbixRestService;
import ru.energomera.zabbixbot.zabbixapi.dto.history.HistoryRequest;
import ru.energomera.zabbixbot.zabbixapi.dto.history.HistoryResponse;
import ru.energomera.zabbixbot.zabbixapi.dto.history.HistoryResult;

public class HistoryCommand implements Command{
    private final SendMessageService sendMessageService;
    private final ZabbixRestService zabbixRestService = new ZabbixRestService(new RestTemplateBuilder());

    public HistoryCommand(SendMessageService sendMessageService) {
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

        HistoryRequest historyRequest = new HistoryRequest(33484, 10);
        HistoryResponse historyResponse = zabbixRestService.createPostWithHistoryObject(historyRequest);
        HistoryResult[] historyResponseResult = historyResponse.getResult();
//        String message = String.format("Колличество полученных объектов - %d", historyResponseResult.length);
//        sendMessageService.sendMessage(chatId, message);
        sendMessageService.sendHistoryPicture(chatId, historyResponseResult, "Proxy server ICMP ping",
                "время", "ответ", "ICMP ping");
    }
}
