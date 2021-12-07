package ru.energomera.zabbixbot.service;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendDice;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.energomera.zabbixbot.sticker.Stickers;
import ru.energomera.zabbixbot.zabbixapi.dto.HistoryResponseResult;

import java.util.List;

public interface SendMessageService {

    Integer sendMessage(String chatId, String message);

//    Integer sendMessageWithReplyMarkDown2(String chatId, String message);
    Long sendMessageWithReplyCopy(String chatId, CopyMessage copyMessage);
    Integer sendMessageWithReplyMarkDown2(String chatId, String message);
    Integer sendMessageWithReplyMarkDown2(String chatId, String message, ForceReplyKeyboard forceReplyKeyboard);

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

    void sendMessageToGroupWithReplyKeyboardMarkupMarDown2(String chatId, String message, ReplyKeyboard keyboard);

    void sendMessageToGroupWithInlineKeyboard(String chatId, String message, ReplyKeyboard keyboard);

    void sendDice(SendDice dice);

    void sendAnswer(AnswerCallbackQuery answer);

    void sendTest(BotApiMethod method);

}

