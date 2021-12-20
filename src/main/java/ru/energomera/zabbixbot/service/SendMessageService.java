package ru.energomera.zabbixbot.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.energomera.zabbixbot.icon.Stickers;
import ru.energomera.zabbixbot.model.zabbix.HistoryResponseResult;

import java.util.List;

/**
 * Интерфейс для отправки сообщений в телеграм
 */
public interface SendMessageService {

    Integer sendMessage(String chatId, String message);

    void sendPrivateMessageWithReplyKeyboardMarkup(String chatId, String message, ReplyKeyboard replyKeyboard);

    void sendEditedMessage(String chatId, String editMessage, Integer oldMessageId);

    Integer sendMessageWithReplyMarkDown2(String chatId, String message);

    Integer sendMessageWithReplyMarkDown2(String chatId, String message, ForceReplyKeyboard forceReplyKeyboard);

    void sendReplyMessage(String chatId, String Message, int messageId);

    void sendSticker(String chatId, Stickers sticker);

    void sendHistoryPictureForManyCharts(String chatId, List<HistoryResponseResult[]> listOfHistoryResponseResults,
                            String chartName, String[] seriesName);

    void sendHistoryPicture(String chatId, HistoryResponseResult[] historyResponseResults,
                                         String chartName, String seriesName);

    void sendCpuUtilizationChart(String chatId, HistoryResponseResult[] historyResponseResults,
                                 String chartName, String seriesName);

    void sendPiePicture(String chatId, HistoryResponseResult[] historyResponseResults,
                        String chartName, String seriesName);

    void sendMessageToGroupWithInlineEditButton(String chatId, String message);

    void editMessageToGroupWithInlineEditButton(String chatId, String newMessage,
                                                Integer originalMessageId);

    void deleteMessageFromChat(String chatId, Integer messageId);

    void sendEmoji(String chatId, String emoji);

    void sendAnswer(String callBackQueryId, String notification);

}

