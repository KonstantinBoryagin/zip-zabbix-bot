package ru.energomera.zabbixbot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import ru.energomera.zabbixbot.sticker.Stickers;
import ru.energomera.zabbixbot.zabbixapi.dto.Result;

public interface SendMessageService {

    void sendMessage(String chatId, String Message);

    void sendSticker(String chatId, Stickers sticker);

    void sendPicture(String chatId, Result[] results);
}
