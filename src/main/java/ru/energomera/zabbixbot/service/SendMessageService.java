package ru.energomera.zabbixbot.service;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.energomera.zabbixbot.sticker.Stickers;
import ru.energomera.zabbixbot.zabbixapi.dto.history.HistoryResult;
import ru.energomera.zabbixbot.zabbixapi.dto.ping.PingResult;

public interface SendMessageService {

    void sendMessageFromWebHook(String chatId, String subject, String message);

    void sendMessageFromWebHookWithCallBackButton(String chatId, String subject, String message, ReplyKeyboard replyKeyboard);

    void sendChangedMessageFromWebHook(EditMessageText editMessageText);

    void sendReplyMessage(String chatId, String Message, int messageId);

    void sendSticker(String chatId, Stickers sticker);

    void sendPingPicture(String chatId, PingResult[] pingResults);

    void sendHistoryPicture(String chatId, HistoryResult[] historyResults, String chartName,
                            String axisXName, String axisYName, String seriesName);

    void sendMessageWithInlineKeyboard(String chatId, String message, ReplyKeyboard keyboard, int messageId);

}
