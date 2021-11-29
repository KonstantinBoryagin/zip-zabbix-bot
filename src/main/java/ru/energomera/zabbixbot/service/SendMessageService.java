package ru.energomera.zabbixbot.service;

import ru.energomera.zabbixbot.sticker.Stickers;
import ru.energomera.zabbixbot.zabbixapi.dto.HistoryResult;
import ru.energomera.zabbixbot.zabbixapi.dto.PingResult;
import ru.energomera.zabbixbot.zabbixapi.dto.Result;

public interface SendMessageService {

    void sendMessage(String chatId, String Message);

    void sendSticker(String chatId, Stickers sticker);

    void sendPingPicture(String chatId, PingResult[] results);
    void sendHistoryPicture(String chatId, HistoryResult[] results);
}
