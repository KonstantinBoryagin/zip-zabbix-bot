package ru.energomera.zabbixbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.energomera.zabbixbot.bot.ZabbixTelegramBot;
import ru.energomera.zabbixbot.sticker.Stickers;
import ru.energomera.zabbixbot.zabbixapi.dto.history.HistoryResult;
import ru.energomera.zabbixbot.zabbixapi.dto.ping.PingResult;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class SendMessageServiceImpl implements SendMessageService {

    private final ZabbixTelegramBot telegramBot;

    @Autowired
    public SendMessageServiceImpl(ZabbixTelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void sendMessageFromWebHook(String chatId, String subject, String message) {

        String text = subject + "\n\n" + message;


        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.enableHtml(true);

        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessageFromWebHookWithCallBackButton(String chatId, String subject, String message, ReplyKeyboard replyKeyboard) {
        String text = subject + "\n\n" + message;


        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.enableHtml(true);

        sendMessage.setReplyMarkup(replyKeyboard);

        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendChangedMessageFromWebHook(EditMessageText editMessageText) {


        try {
            telegramBot.execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void sendReplyMessage(String chatId, String message, int messageId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.enableHtml(true);
        sendMessage.setReplyToMessageId(messageId);

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
    public void sendHistoryPicture(String chatId, HistoryResult[] historyResults,
                                   String chartName, String axisXName, String axisYName,
                                   String seriesName) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        InputFile inputPicture = new InputFile();
        ChartService chartService = new ChartService();

        File picture = null;
        try {
            picture = chartService.createHistoryPicture(historyResults, chartName, axisXName, axisYName, seriesName);
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
    public void sendMessageWithInlineKeyboard(String chatId, String message, ReplyKeyboard keyboard, int messageId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.enableHtml(true);
        sendMessage.setReplyToMessageId(messageId);

        sendMessage.setReplyMarkup(keyboard);

        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
