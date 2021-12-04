package ru.energomera.zabbixbot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.energomera.zabbixbot.command.CommandContainer;
import ru.energomera.zabbixbot.zabbixapi.dto.ZabbixWebHook;

import java.util.ArrayList;
import java.util.List;

import static ru.energomera.zabbixbot.command.CommandName.PROXY_PING_COMMAND;
import static ru.energomera.zabbixbot.command.KeyWordsAndTags.*;
import static ru.energomera.zabbixbot.sticker.Icon.EXCLAMATION;

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

    public void processMessageFor25Department(ZabbixWebHook webHookEntity) {
        String chatId = webHookEntity.getChat_id();
        String subject = EXCLAMATION.get() + webHookEntity.getSubj() + EXCLAMATION.get();
        String message = subject  + "\n\n" + webHookEntity.getMessage();

        List<List<InlineKeyboardButton>> keyboardList = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(InlineKeyboardButton.builder().text("Обновить сведения").callbackData("/update").build());
        row.add(InlineKeyboardButton.builder().text("Закрыть инцендент").callbackData("/close").build());

        keyboardList.add(row);
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(keyboardList);

        sendMessageService.sendMessageToGroupWithInlineKeyboard(chatId, message, keyboard);
    }

    private void formProxyMessage(String chatId, String subject, String message) {
        if (subject.contains(PROBLEM.getKeyWord())) {
            commandContainer.retrieveChart(PROXY_PING_COMMAND.getCommandName()).sendChart(chatId, subject, message);
        } else if (subject.contains(SOLVED.getKeyWord())) {
            sendMessageService.sendMessageFromWebHook(chatId, subject, message);
        }


    }
}
