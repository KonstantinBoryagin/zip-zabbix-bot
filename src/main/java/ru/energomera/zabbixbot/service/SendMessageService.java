package ru.energomera.zabbixbot.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.energomera.zabbixbot.sticker.Stickers;
import ru.energomera.zabbixbot.zabbixapi.dto.HistoryResponseResult;

import java.util.List;

public interface SendMessageService {

    Integer sendMessage(String chatId, String message);

    void sendPrivateMessageWithReplyKeyboardMarkup(String chatId, String message, ReplyKeyboard replyKeyboard); //use

    void sendEditedMessage(String chatId, String editMessage, Integer oldMessageId); //use

//    Integer sendMessageWithReplyMarkDown2(String chatId, String message);

    Integer sendMessageWithReplyMarkDown2(String chatId, String message);//use
    Integer sendMessageWithReplyMarkDown2(String chatId, String message, ForceReplyKeyboard forceReplyKeyboard);//use

//    void sendMessageFromWebHook(String chatId, String subject, String message);
//
//    void sendMessageFromWebHookWithCallBackButton(String chatId, String subject, String message, ReplyKeyboard replyKeyboard);
//
//    void sendChangedMessageFromWebHook(EditMessageText editMessageText);

    void sendReplyMessage(String chatId, String Message, int messageId);

    void sendSticker(String chatId, Stickers sticker);

    void sendHistoryPictureForManyCharts(String chatId, List<HistoryResponseResult[]> listOfHistoryResponseResults,
                            String chartName, String[] seriesName);

    void sendHistoryPicture(String chatId, HistoryResponseResult[] historyResponseResults,
                                         String chartName, String seriesName);

    void sendCpuUtilization(String chatId, HistoryResponseResult[] historyResponseResults,
                            String chartName, String seriesName);

    void sendPiePicture(String chatId, HistoryResponseResult[] historyResponseResults,
                        String chartName, String seriesName);

//    void sendHistoryPictureWithText(String chatId, String subject, String message,
//                                    HistoryResponseResult[] historyResponseResults, String chartName, String seriesName);

//    void sendMessageToGroupWithReplyKeyboardMarkup(String chatId, String message, ReplyKeyboard keyboard, int messageId);

    void sendMessageToGroupWithInlineEditButton(String chatId, String message); // отправляет для цехов с кнопкой
    void editMessageToGroupWithInlineEditButton(String chatId, String newMessage,
                                                Integer originalMessageId);  // отправка изминенного для цехов с кнопкой
    void deleteMessageFromChat(String chatId, Integer messageId); // delete message

    void sendEmoji(String chatId, String emoji);

    void sendAnswer(String callBackQueryId, String notification);//use

}

