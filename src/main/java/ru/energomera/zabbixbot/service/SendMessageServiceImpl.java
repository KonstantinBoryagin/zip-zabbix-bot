package ru.energomera.zabbixbot.service;

import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendDice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageId;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.energomera.zabbixbot.bot.ZabbixTelegramBot;
import ru.energomera.zabbixbot.sticker.Stickers;
import ru.energomera.zabbixbot.zabbixapi.dto.HistoryResponseResult;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class SendMessageServiceImpl implements SendMessageService {

    private final ZabbixTelegramBot telegramBot;

    @Autowired
    public SendMessageServiceImpl(ZabbixTelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }


    @Override
    public Integer sendMessageWithReplyMarkDown2(String chatId, String message) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(message)
                .parseMode(ParseMode.MARKDOWNV2)
                .disableWebPagePreview(false)
                .build();

        ForceReplyKeyboard build = ForceReplyKeyboard.builder()
                .inputFieldPlaceholder("Введите здесь свое сообщение")   //появится в поле ввода у пользователя
//                .selective(true)  //нужно где то взять ид сообщения или юзера
                .forceReply(true).build();

        ReplyKeyboardRemove build1 = ReplyKeyboardRemove.builder().removeKeyboard(true).selective(true).build();


        sendMessage.setReplyMarkup(build);


        try {
            Message execute = telegramBot.execute(sendMessage);
            Integer newMessageId = execute.getMessageId();
            System.out.println(newMessageId + "   sendMessageWithReply worked success"); //temp
            return newMessageId;

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Long sendMessageWithReplyCopy(String chatId, CopyMessage copyMessage) {

        ForceReplyKeyboard build = ForceReplyKeyboard.builder()
                .inputFieldPlaceholder("Введите здесь свое сообщение")   //появится в поле ввода у пользователя
//                .selective(true)  //нужно где то взять ид сообщения или юзера
                .forceReply(true).build();

        ReplyKeyboardRemove build1 = ReplyKeyboardRemove.builder().removeKeyboard(true).selective(true).build();


        copyMessage.setReplyMarkup(build);


        try {
            MessageId execute = telegramBot.execute(copyMessage);
            Long messageId = execute.getMessageId();
            System.out.println(messageId + "   sendMessageWithReply worked success"); //temp
            return messageId;

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer sendMessageWithReplyMarkDown2(String chatId, String message, Integer messageId) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(message)
                .replyToMessageId(messageId)
                .disableWebPagePreview(false)
                .build();

        ForceReplyKeyboard build = ForceReplyKeyboard.builder()
                .inputFieldPlaceholder("Введите ")//появится в поле ввода у пользователя
//                .selective(true)  //нужно где то взять ид сообщения или юзера
                .forceReply(true)
                .build();

//        ReplyKeyboardRemove build1 = ReplyKeyboardRemove.builder().removeKeyboard(true).selective(true).build();


        sendMessage.setReplyMarkup(build);


        try {
            Message execute = telegramBot.execute(sendMessage);
            Integer newMessageId = execute.getMessageId();
            System.out.println(newMessageId + "   sendMessageWithReply worked success"); //temp
            return newMessageId;

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Integer sendMessage(String chatId, String message) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(message)
                .disableWebPagePreview(false)
                .build();

        try {
            Message execute = telegramBot.execute(sendMessage);
            Integer messageId = execute.getMessageId();
            System.out.println(messageId + "   sendMessage worked success"); //temp
            return messageId;

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
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
    public void sendHistoryPicture(String chatId, List<HistoryResponseResult[]> listOfHistoryResponseResults,
                                   String chartName, String[] seriesName) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        InputFile inputPicture = new InputFile();
        ChartService chartService = new ChartService();

        //создаем набор данных графика
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        //наполняем его значениями
        for (int i = 0; i < listOfHistoryResponseResults.size(); i++) {
            dataset = chartService.createIcmpPingDataset(dataset, listOfHistoryResponseResults.get(i), seriesName[i]);
        }

        File picture = null;
        try {
            picture = chartService.createIcmpPingLineChartPicture(dataset, chartName);
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
    public void sendHistoryPictureWithText(String chatId, String subject, String message, HistoryResponseResult[] historyResponseResults,
                                           String chartName, String seriesName) {

        String text = subject + "\n\n" + message;

        InputFile inputPicture = new InputFile();
        ChartService chartService = new ChartService();

        //создаем набор данных графика
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        //наполняем его значениями
        dataset = chartService.createIcmpPingDataset(dataset, historyResponseResults, seriesName);

        File picture = null;
        try {
            picture = chartService.createIcmpPingLineChartPicture(dataset, chartName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputPicture.setMedia(picture);

        SendPhoto post = SendPhoto.builder()
                .chatId(chatId)
                .caption(text)
                .photo(inputPicture)
                .build();

        try {
            telegramBot.execute(post);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void sendMessageToGroupWithReplyKeyboardMarkup(String chatId, String message, ReplyKeyboard keyboard, int messageId) {
        SendMessage sendMessage = SendMessage.builder()
                .replyToMessageId(messageId)
                .chatId(chatId)
                .text("Menu")
                .replyMarkup(keyboard)
                .build();

        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessageToGroupWithReplyKeyboardMarkupMarDown2(String chatId, String message, ReplyKeyboard keyboard) {


    }

    @Override
    public void sendMessageToGroupWithInlineKeyboard(String chatId, String message, ReplyKeyboard keyboard) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(message)
                .disableWebPagePreview(false)
                .parseMode(ParseMode.HTML)
                .replyMarkup(keyboard)
                .build();

        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendDice(SendDice dice) {

        try {
            telegramBot.execute(dice);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendAnswer(AnswerCallbackQuery answer) {


        try {
            telegramBot.execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendTest(BotApiMethod method) {
        try {
            telegramBot.execute(method);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
