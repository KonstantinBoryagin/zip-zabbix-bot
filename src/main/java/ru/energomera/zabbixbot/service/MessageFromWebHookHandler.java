package ru.energomera.zabbixbot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.energomera.zabbixbot.command.CommandContainer;
import ru.energomera.zabbixbot.zabbixapi.dto.ZabbixWebHook;

import static ru.energomera.zabbixbot.command.CommandName.PROXY_PING_COMMAND;
import static ru.energomera.zabbixbot.command.KeyWordsAndTags.*;

@Service
public class MessageFromWebHookHandler {

    private SendMessageService sendMessageService;
    private CommandContainer commandContainer;


    @Autowired
    public MessageFromWebHookHandler(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
        commandContainer = new CommandContainer(this.sendMessageService);
    }

    public void processInputMessage(ZabbixWebHook webHookEntity) {
        String chatId = webHookEntity.getChat_id();
        String subject = webHookEntity.getSubj();
        String message = webHookEntity.getMessage();

        if (subject.contains(PROXY_PING.getKeyWord())) {
            message += PROXY_PING.getTag();
            formProxyMessage(chatId, subject, message);
        } else {
            sendMessageService.sendMessageFromWebHook(chatId, subject, message);
        }

    }

    private void formProxyMessage(String chatId, String subject, String message) {
        if (subject.contains(PROBLEM.getKeyWord())) {
            commandContainer.retrieveChart(PROXY_PING_COMMAND.getCommandName()).sendChart(chatId, subject, message);
        } else if (subject.contains(SOLVED.getKeyWord())) {
            sendMessageService.sendMessageFromWebHook(chatId, subject, message);
        }


    }
}
