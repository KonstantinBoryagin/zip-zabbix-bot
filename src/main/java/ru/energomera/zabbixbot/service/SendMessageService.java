package ru.energomera.zabbixbot.service;

import ru.energomera.zabbixbot.sticker.Stickers;
import ru.energomera.zabbixbot.zabbixapi.dto.history.HistoryResult;
import ru.energomera.zabbixbot.zabbixapi.dto.ping.PingResult;

public interface SendMessageService {

    void sendMessage(String chatId, String Message);

    void sendSticker(String chatId, Stickers sticker);

    void sendPingPicture(String chatId, PingResult[] pingResults);
    void sendHistoryPicture(String chatId, HistoryResult[] historyResults, String chartName, String axisXName, String axisYName);
}
