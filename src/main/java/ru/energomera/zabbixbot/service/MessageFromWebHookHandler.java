package ru.energomera.zabbixbot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.energomera.zabbixbot.zabbixapi.dto.ZabbixWebHook;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static ru.energomera.zabbixbot.sticker.Icon.*;

@Service
public class MessageFromWebHookHandler {

    private SendMessageService sendMessageService;
//    private CommandContainer commandContainer;
    public static Map<String, List<List<Object>>> messagesRepository = new LinkedHashMap<>();
    private String saveRuleException = "LogitemTrigger   Важность: Not classified";

    @Autowired
    public MessageFromWebHookHandler(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
//        commandContainer = new CommandContainer(this.sendMessageService);
    }

    public void processMessageForAdminGroup(ZabbixWebHook webHookEntity) {
        String chatId = webHookEntity.getChat_id();
        String subject = webHookEntity.getSubj();
        String message = webHookEntity.getMessage();

        saveAndSendMessageForAdminGroup(chatId, subject, message);

//        if (subject.contains(PROXY_PING.getKeyWord())) {
//            message += PROXY_PING.getTag();
//            formProxyMessage(chatId, subject, message);
//        } else {
//            sendMessageService.sendMessageFromWebHook(chatId, subject, message);
//        }

    }

    private void saveAndSendMessageForAdminGroup(String chatId, String subject, String message) {
        String[] splitResult = subject.split(":", 2);

        String keyWord = splitResult[0];        //Проблема или Решено
        String incidentText = splitResult[1] + "\n" + message;   //остальной текст

        switch (keyWord) {
            case "Проблема":
                String problemMessage = FLAME.get() + "  <b>" + keyWord + ": <i>" + splitResult[1] + "</i></b>\n\n" + message;
                Integer sendMessageId = sendMessageService.sendMessage(chatId, problemMessage);

                if(message.equals(saveRuleException)) {    //исключение, на нее не приходит "Решено"
                    break;
                } else if(messagesRepository.containsKey(incidentText)){
                    List<List<Object>> allMessagesProperties = messagesRepository.get(incidentText);
                    List<Object> messageProperties = new ArrayList<>();

                    long startProblem = System.currentTimeMillis();

                    messageProperties.add(sendMessageId);
                    messageProperties.add(startProblem);

                    allMessagesProperties.add(messageProperties);
                } else {
                    List<List<Object>> allMessagesProperties = new ArrayList<>();
                    List<Object> messageProperties = new ArrayList<>();

                    long startProblem = System.currentTimeMillis();

                    messageProperties.add(sendMessageId);
                    messageProperties.add(startProblem);

                    allMessagesProperties.add(messageProperties);
                    messagesRepository.put(incidentText, allMessagesProperties);   //save to map
                }
                break;
            case "Решено":
                if (messagesRepository.containsKey(incidentText)) {
                    List<List<Object>> allMessagesProperties = messagesRepository.get(incidentText);

                    List<Object> oldMessageProperties = allMessagesProperties.remove(0);
                    Integer oldMessageId = (Integer) oldMessageProperties.get(0);
                    long problemStart = (long) oldMessageProperties.get(1);
                    long problemTime = System.currentTimeMillis() - problemStart;

                    String formatProblemTime = String.format("%02dh:%02dm:%02ds", problemTime / 1000 / 3600,
                            problemTime / 1000 / 60 % 60, problemTime / 1000 % 60);

                    String solvedMessage = CHECK.get() + "  <b>" + keyWord + ": <i>" + splitResult[1] + "</i></b>\n\n"
                            + message + "\n\n" + CLOCK_2.get() + "<i>  " + formatProblemTime + "</i>";
                    sendMessageService.sendEditedMessage(chatId, solvedMessage, oldMessageId);

                    if(allMessagesProperties.size() == 0) {
                        messagesRepository.remove(incidentText);  //clear map if list is empty
                    }
                } else {
                    String editMessage = CHECK.get() + "  <b>" + keyWord + ": <i>" + splitResult[1] + "</i></b>\n\n" + message;
                    sendMessageService.sendMessage(chatId, editMessage);
                }
        }

    }

    public void processMessageForDepartmentNotifications(ZabbixWebHook webHookEntity) {
        String chatId = webHookEntity.getChat_id();
        String subject = "<b>" + EXCLAMATION.get() + webHookEntity.getSubj().trim() + EXCLAMATION.get() + "</b>";
        String text = parseZabbixWebhookMessage(webHookEntity.getMessage());
//        String hashtag = resultForParsing[1];
        String message = subject + "\n\n" + text;


        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(formDefaultKeyboard());

        sendMessageService.sendMessageToGroupWithInlineKeyboard(chatId, message, keyboard);
    }

//    private void formProxyMessage(String chatId, String subject, String message) {
//        if (subject.contains(PROBLEM.getKeyWord())) {
//            commandContainer.retrieveChart(PROXY_PING_COMMAND.getCommandName()).sendChart(chatId, subject, message);
//        } else if (subject.contains(SOLVED.getKeyWord())) {
//            sendMessageService.sendMessageFromWebHook(chatId, subject, message);
//        }
//    }

    public static List<List<InlineKeyboardButton>> addInlineKeyboardToGroupNotificationPost() {
        List<List<InlineKeyboardButton>> keyboardList = formDefaultKeyboard();

        ////////////////////////
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(InlineKeyboardButton.builder().text(PUSHPIN.get() + "Switch").callbackData("/change|1").build());
        //////////////////////


        /////////////
        keyboardList.add(row2);

        return keyboardList;
    }

//    public static List<InlineKeyboardButton> inlineChangeButton(Update update) {
//        String text = update.getCallbackQuery().getMessage().getText();
//        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
//        String messageId = update.getCallbackQuery().getMessage().getMessageId().toString();
//        User user = update.getCallbackQuery().getFrom();
//        List<String> userList = new ArrayList<>();
//        Collections.addAll(userList, text, chatId, messageId);
//
//        userPrivateChoose.put(user, userList);
//
////        String query = "Edit message|" + text + chatId + messageId + "|";
////        System.out.println(query + " ----- query from button inline change mode");
//
//        List<InlineKeyboardButton> row = new ArrayList<>();
//        row.add(InlineKeyboardButton.builder().text(PUSHPIN.get() + "Switch").switchInlineQueryCurrentChat("").build());
//
//        return row;
//    }

    public static List<List<InlineKeyboardButton>> formDefaultKeyboard() {
        List<List<InlineKeyboardButton>> keyboardList = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(InlineKeyboardButton.builder().text(PUSHPIN.get() + "Дополнить сообщение").callbackData("/update").build());
        keyboardList.add(row);
        return keyboardList;
    }

    private String parseZabbixWebhookMessage(String text) {
        String message = "";
        String incidentNumber = "";
        String[] splitText = text.split("\\|");
        for (int i = 0; i < splitText.length; i++) {
            String[] splitLine = splitText[i].split(":");

            if (splitLine.length == 2) {
                if (splitLine[0].equals("ИД")) {
                    incidentNumber = splitLine[1].trim();
                }
                message += "<b>" + splitLine[0].trim() + ":</b> " + splitLine[1].trim() + "\n";
            } else {
                message += "<b>" + splitLine[0].trim() + ":</b> " + splitLine[1].trim() + ":" + splitLine[2].trim()
                        + ":" + splitLine[3].trim() + "\n";                                                           //для времени что бы отсечь секунды - 0000

            }
        }
        message += "\n<i>#incident_" + incidentNumber + "</i>";

        return message;
    }
}
