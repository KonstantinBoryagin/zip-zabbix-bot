package ru.energomera.zabbixbot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.energomera.zabbixbot.command.CommandContainer;
import ru.energomera.zabbixbot.zabbixapi.dto.ZabbixWebHook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.energomera.zabbixbot.command.CommandName.PROXY_PING_COMMAND;
import static ru.energomera.zabbixbot.command.KeyWordsAndTags.*;
import static ru.energomera.zabbixbot.command.TempInlineCommand.userPrivateChoose;
import static ru.energomera.zabbixbot.sticker.Icon.*;

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
        String subject = "<strong>" + EXCLAMATION.get() + webHookEntity.getSubj().trim() + EXCLAMATION.get() + "</strong>";
        String text = parseZabbixWebhookMessage(webHookEntity.getMessage());
        String message = subject  + "\n\n" + text;


        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(addInlineKeyboardToGroupNotificationPost());



        sendMessageService.sendMessageToGroupWithInlineKeyboard(chatId, message, keyboard);
    }

    private void formProxyMessage(String chatId, String subject, String message) {
        if (subject.contains(PROBLEM.getKeyWord())) {
            commandContainer.retrieveChart(PROXY_PING_COMMAND.getCommandName()).sendChart(chatId, subject, message);
        } else if (subject.contains(SOLVED.getKeyWord())) {
            sendMessageService.sendMessageFromWebHook(chatId, subject, message);
        }
    }

    public static List<List<InlineKeyboardButton>>  addInlineKeyboardToGroupNotificationPost() {
        List<List<InlineKeyboardButton>> keyboardList = formDefaultKeyboard();

        ////////////////////////
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(InlineKeyboardButton.builder().text(PUSHPIN.get() + "Switch").callbackData("/change|1").build());
        //////////////////////



        /////////////
        keyboardList.add(row2);

        return keyboardList;
    }

    public static List<InlineKeyboardButton> inlineChangeButton(Update update) {
        String text = update.getCallbackQuery().getMessage().getText();
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        String messageId = update.getCallbackQuery().getMessage().getMessageId().toString();
        User user = update.getCallbackQuery().getFrom();
        List<String> userList = new ArrayList<>();
        Collections.addAll(userList, text, chatId, messageId);

        userPrivateChoose.put(user, userList);

//        String query = "Edit message|" + text + chatId + messageId + "|";
//        System.out.println(query + " ----- query from button inline change mode");

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(InlineKeyboardButton.builder().text(PUSHPIN.get() + "Switch").switchInlineQueryCurrentChat("").build());

        return row;
    }

    public static List<List<InlineKeyboardButton>> formDefaultKeyboard() {
        List<List<InlineKeyboardButton>> keyboardList = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(InlineKeyboardButton.builder().text(PUSHPIN.get() + "Дополнить").callbackData("/update|1").build());
        row.add(InlineKeyboardButton.builder().text(WHITE_CHECK_MARK.get() + "Закрыть").callbackData("/edit").build());
        keyboardList.add(row);
        return keyboardList;
    }

    private String parseZabbixWebhookMessage(String text) {
        String message = "";
        String[] splitText = text.split("\\|");
        for (int i = 0; i < splitText.length; i++) {
            String[] splitLine = splitText[i].split(":");

            if(splitLine.length == 2) {
                message += "<b>" + splitLine[0].trim() + ": </b>" + splitLine[1].trim() + "\n";
            } else {
                message += "<b>" + splitLine[0].trim() + ": </b>" + splitLine[1].trim() + ":" + splitLine[2].trim()
                        + ":" + splitLine[3].trim() + "\n";                                                           //для времени что бы отсечь секунды - 0000

            }
        }

        return message;
    }
}
