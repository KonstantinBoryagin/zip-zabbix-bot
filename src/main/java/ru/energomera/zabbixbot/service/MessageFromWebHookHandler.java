package ru.energomera.zabbixbot.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.energomera.zabbixbot.zabbixapi.dto.ZabbixWebHook;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static ru.energomera.zabbixbot.sticker.Icon.*;

@Service
@Slf4j
public class MessageFromWebHookHandler {

    private final SendMessageService sendMessageService;
    public static Map<String, List<List<Object>>> messagesRepository = new LinkedHashMap<>();
    private final String saveRuleException1 = "LogitemTrigger";
    private final String saveRuleException2 = "Важность: Not classified";

    @Autowired
    public MessageFromWebHookHandler(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    public void processMessageForAdminGroup(ZabbixWebHook webHookEntity) {
        String chatId = webHookEntity.getChat_id();
        String subject = webHookEntity.getSubj();
        String message = webHookEntity.getMessage();

        saveAndSendMessageForAdminGroup(chatId, subject, message);
    }

    private void saveAndSendMessageForAdminGroup(String chatId, String subject, String message) {
        String[] splitResult = subject.split(":", 2);

        String keyWord = splitResult[0];        //Проблема или Решено
        String incidentText = splitResult[1] + "\n" + message;   //остальной текст

        switch (keyWord) {
            case "Проблема":
                String problemMessage = FLAME.get() + "  <b>" + keyWord + ": <i>" + splitResult[1] + "</i></b>\n\n" + message;

                Integer sendMessageId = sendMessageService.sendMessage(chatId, problemMessage);

                //(sendMessageId != null) проверка на то что сообщение отправлено
                //так как там где бот будет деплоиться часто бывают сбои при связи с телеграмм(из-за загруженности канала)
                if (message.contains(saveRuleException1) && message.contains(saveRuleException2)) {
                    //исключение, на нее не приходит "Решено"
                    log.info("Problem message id {} don't save because it is saveRuleException", sendMessageId);
                    break;
                } else if (sendMessageId != null && messagesRepository.containsKey(incidentText)) {
                    List<List<Object>> allMessagesProperties = messagesRepository.get(incidentText);
                    List<Object> messageProperties = new ArrayList<>();

                    long startProblem = System.currentTimeMillis();

                    messageProperties.add(sendMessageId);
                    messageProperties.add(startProblem);

                    allMessagesProperties.add(messageProperties);
                    log.info("Problem message with id {} save like recurring problem, List size - {}", sendMessageId, allMessagesProperties.size());
                } else if (sendMessageId != null) {
                    List<List<Object>> allMessagesProperties = new ArrayList<>();
                    List<Object> messageProperties = new ArrayList<>();

                    long startProblem = System.currentTimeMillis();

                    messageProperties.add(sendMessageId);
                    messageProperties.add(startProblem);

                    allMessagesProperties.add(messageProperties);
                    messagesRepository.put(incidentText, allMessagesProperties);   //save to map
                    log.info("Problem message with id {} save like first problem", sendMessageId);
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

                    log.info("Resolved message delete entry problem from Map, List size - {}", allMessagesProperties.size());

                    if (allMessagesProperties.size() == 0) {
                        messagesRepository.remove(incidentText);  //clear map if list is empty
                        log.info("Clear problem Map because List with incidents is empty");
                    }
                } else {
                    String editMessage = CHECK.get() + "  <b>" + keyWord + ": <i>" + splitResult[1] + "</i></b>\n\n" + message;
                    sendMessageService.sendMessage(chatId, editMessage);
                    log.warn("Resolved problem send without saving because in Map no entry found, text ({})", editMessage);
                }
        }

    }

    public void processMessageForDepartmentNotifications(ZabbixWebHook webHookEntity) {
        String chatId = webHookEntity.getChat_id();
        String subject = "<b>" + EXCLAMATION.get() + webHookEntity.getSubj().trim() + EXCLAMATION.get() + "</b>";
        String text = parseZabbixWebhookMessage(webHookEntity.getMessage());
        String message = subject + "\n\n" + text;

        sendMessageService.sendMessageToGroupWithInlineEditButton(chatId, message);
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
                        + ":" + splitLine[3].trim() + "\n";          //для времени что бы отсечь секунды - 0000

            }
        }
        message += "\n<i>#incident_" + incidentNumber + "</i>";

        return message;
    }
}
