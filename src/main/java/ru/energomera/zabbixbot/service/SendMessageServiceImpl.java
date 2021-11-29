package ru.energomera.zabbixbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.energomera.zabbixbot.bot.ZabbixTelegramBot;
import ru.energomera.zabbixbot.sticker.Stickers;
import ru.energomera.zabbixbot.zabbixapi.dto.HistoryResult;
import ru.energomera.zabbixbot.zabbixapi.dto.PingResult;
import ru.energomera.zabbixbot.zabbixapi.dto.Result;

import java.io.*;

@Service
public class SendMessageServiceImpl implements SendMessageService {

    private final ZabbixTelegramBot telegramBot;

    @Autowired
    public SendMessageServiceImpl(ZabbixTelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.enableHtml(true);

        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendSticker(String chatId, Stickers sticker) {
        SendSticker sendSticker = new SendSticker();
        sendSticker.setChatId(chatId);
        InputFile stickerPath = new InputFile(sticker.getStickerId());
        sendSticker.setSticker(stickerPath);

        try {
            telegramBot.execute(sendSticker);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendPingPicture(String chatId, PingResult[] pingResults) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        InputFile inputPicture = new InputFile();
        ChartService chartService = new ChartService();

        File picture = null;
        try {
            picture = chartService.createPingPicture(pingResults);
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputPicture.setMedia(picture);
        sendPhoto.setPhoto(inputPicture);

        try {
            telegramBot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendHistoryPicture(String chatId, HistoryResult[] historyResults) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        InputFile inputPicture = new InputFile();
        ChartService chartService = new ChartService();

        File picture = null;
        try {
            picture = chartService.createHistoryPicture(historyResults);
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputPicture.setMedia(picture);
        sendPhoto.setPhoto(inputPicture);

        try {
            telegramBot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
