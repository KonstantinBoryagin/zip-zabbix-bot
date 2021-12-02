package ru.energomera.zabbixbot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendDice;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.energomera.zabbixbot.sticker.Stickers;
import ru.energomera.zabbixbot.zabbixapi.dto.HistoryResponseResult;

import java.util.List;

public interface SendMessageService {

    void sendMessage(String chatId, String message);

    void sendMessageFromWebHook(String chatId, String subject, String message);

    void sendMessageFromWebHookWithCallBackButton(String chatId, String subject, String message, ReplyKeyboard replyKeyboard);

    void sendChangedMessageFromWebHook(EditMessageText editMessageText);

    void sendReplyMessage(String chatId, String Message, int messageId);

    void sendSticker(String chatId, Stickers sticker);

    void sendHistoryPicture(String chatId, List<HistoryResponseResult[]> listOfHistoryResponseResults,
                            String chartName, String[] seriesName);

    void sendHistoryPictureWithText(String chatId, String subject, String message,
                                    HistoryResponseResult[] historyResponseResults, String chartName, String seriesName);

    void sendMessageToGroupWithReplyKeyboardMarkup(String chatId, String message, ReplyKeyboard keyboard, int messageId);

    void sendDice(SendDice dice);

}

