package ru.energomera.zabbixbot.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.energomera.zabbixbot.zabbixapi.dto.ZabbixWebHook;

@Service
public class MessageFromWebHookHandler {

    private SendMessageService sendMessageService;

    public MessageFromWebHookHandler(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    public void processInputMessage(ZabbixWebHook webHookEntity) {
        String chatId = webHookEntity.getChat_id();
        String message = webHookEntity.getText();

//        String[] split = line.split(System.getProperty("line.separator"));

        sendMessageService.sendMessage(chatId, message);
    }


}
