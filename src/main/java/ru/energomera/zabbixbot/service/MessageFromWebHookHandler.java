package ru.energomera.zabbixbot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.energomera.zabbixbot.command.ProxyPingCommand;
import ru.energomera.zabbixbot.zabbixapi.dto.ZabbixWebHook;

import static ru.energomera.zabbixbot.command.KeyWordsAndTags.PROXY_PING;

@Service
public class MessageFromWebHookHandler {

    private SendMessageService sendMessageService;

    @Autowired
    public MessageFromWebHookHandler(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    public void processInputMessage(ZabbixWebHook webHookEntity) {
        String chatId = webHookEntity.getChat_id();
        String subject = webHookEntity.getSubj();
        String message = webHookEntity.getMessage();

//        String[] split = line.split(System.getProperty("line.separator"));


        if (subject.contains(PROXY_PING.getKeyWord())) {
               ProxyPingCommand proxyPingCommand = new ProxyPingCommand(sendMessageService);
               proxyPingCommand.sendChart(chatId);

               message += PROXY_PING.getTag();
                sendMessageService.sendMessageFromWebHook(chatId, subject, message);
//            sendMessageService.sendMessageFromWebHookWithCallBackButton(chatId, subject, message, proxyShowButton);
        } else if (subject.contains("test")) {
            sendMessageService.sendMessageFromWebHook(chatId, subject, message);
        } else if (subject.contains("fall")) {

        } else {
            sendMessageService.sendMessageFromWebHook(chatId, subject, message);
        }
    }


}
