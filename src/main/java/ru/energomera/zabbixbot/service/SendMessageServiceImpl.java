package ru.energomera.zabbixbot.service;

import lombok.extern.slf4j.Slf4j;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendDice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.energomera.zabbixbot.bot.ZabbixTelegramBot;
import ru.energomera.zabbixbot.icon.Stickers;
import ru.energomera.zabbixbot.model.zabbix.HistoryResponseResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.energomera.zabbixbot.icon.Icon.PUSHPIN;

/**
 * Сервис для формирования и отправки сообщений {@link SendMessageService}
 */
@Service
@Slf4j
public class SendMessageServiceImpl implements SendMessageService {

    private final ZabbixTelegramBot telegramBot;
    private final ChartService chartService;

    @Autowired
    public SendMessageServiceImpl(ZabbixTelegramBot telegramBot) {
        this.telegramBot = telegramBot;
        chartService = new ChartService();
    }

    /**
     * Отправляет сообщение с Mark Down 2 разметкой
     *
     * @param chatId  id чата
     * @param message сообщение для отправки
     * @return id отправленного сообщения
     */
    @Override
    public Integer sendMessageWithReplyMarkDown2(String chatId, String message) {

        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(message)
                .disableNotification(true)
                .parseMode(ParseMode.MARKDOWNV2)
                .build();

        try {
            Message execute = telegramBot.execute(sendMessage);
            return execute.getMessageId();
        } catch (TelegramApiException e) {
            log.error("sendMessageWithReplyMarkDown2 could not sent {}", message, e);
        }
        return null;
    }

    /**
     * Отправляет сообщение с Mark Down 2 разметкой и всплывающем в чате уведомлением
     *
     * @param chatId             id чата
     * @param message            сообщение для отправки
     * @param forceReplyKeyboard
     * @return id отправленного сообщения
     */
    @Override
    public Integer sendMessageWithReplyMarkDown2(String chatId, String message, ForceReplyKeyboard forceReplyKeyboard) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(message)
                .replyMarkup(forceReplyKeyboard)
                .disableNotification(true)
                .parseMode(ParseMode.MARKDOWNV2)
                .build();

        try {
            Message execute = telegramBot.execute(sendMessage);
            return execute.getMessageId();
        } catch (TelegramApiException e) {
            log.error("sendMessageWithReplyMarkDown2 could not sent {}", message, e);
        }
        return null;
    }


    /**
     * Отправляет сообщение с HTML разметкой в чат
     *
     * @param chatId  id чата
     * @param message сообщение для отправки
     * @return id отправленного сообщения
     */
    @Override
    public Integer sendMessage(String chatId, String message) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(message)
                .disableWebPagePreview(false)
                .parseMode(ParseMode.HTML)
                .build();

        try {
            Message execute = telegramBot.execute(sendMessage);
            Integer messageId = execute.getMessageId();
            log.info("message with id {} sent successfully", messageId);
            return messageId;
        } catch (TelegramApiException e) {
            log.error("Can't send message {}", message, e);
        }
        return null;
    }

    /**
     * Отправляет сообщение и клавиатуру с кнопками (в случае телефона заменяет клавиатуру ввода символов)
     *
     * @param chatId        id чата
     * @param message       сообщение для отправки
     * @param replyKeyboard клавиатура с кнопками
     */
    @Override
    public void sendPrivateMessageWithReplyKeyboardMarkup(String chatId, String message, ReplyKeyboard replyKeyboard) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(message)
                .replyMarkup(replyKeyboard)
                .parseMode(ParseMode.HTML)
                .build();
        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Can't send replyKeyboard to private chat {}", chatId, e);
        }

    }

    /**
     * Редактирует сообщение (по полученному id), заменяя старый текст на (полученный) новый
     *
     * @param chatId       id чата
     * @param editMessage  новое сообщение
     * @param oldMessageId id сообщения, которое будет отредактировано
     */
    @Override
    public void sendEditedMessage(String chatId, String editMessage, Integer oldMessageId) {
        EditMessageText editMessageText = EditMessageText.builder()
                .chatId(chatId)
                .messageId(oldMessageId)
                .parseMode(ParseMode.HTML)
                .text(editMessage)
                .build();

        try {
            telegramBot.execute(editMessageText);
        } catch (TelegramApiException e) {
            log.error("sendEditedMessage could not sent {}", editMessage, e);
        }
    }

    /**
     * Отправляет ответ на конкретное сообщение
     *
     * @param chatId    id чата
     * @param message   сообщение
     * @param messageId id сообщения на который будет отправлен ответ
     */
    @Override
    public void sendReplyMessage(String chatId, String message, int messageId) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(message)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(false)
                .replyToMessageId(messageId)
                .build();

        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Could not sent replyMessage: {} to {}", message, messageId, e);
        }
    }

    /**
     * Отправляет стикер
     *
     * @param chatId  id чата
     * @param sticker стикер
     */
    @Override
    public void sendSticker(String chatId, Stickers sticker) {
        SendSticker sendSticker = new SendSticker();
        sendSticker.setChatId(chatId);
        InputFile stickerPath = new InputFile(sticker.getStickerId());
        sendSticker.setSticker(stickerPath);

        try {
            telegramBot.execute(sendSticker);
        } catch (TelegramApiException e) {
            log.error("could not sent sticker to {}", chatId, e);
        }
    }

    /**
     * Формирует и отправляет картинку с несколькими линейными графиками
     *
     * @param chatId                       id чата
     * @param listOfHistoryResponseResults данные для формирования графика
     * @param chartName                    имя графика
     * @param seriesName                   подпись линии на графике
     */
    @Override
    public void sendHistoryPictureForManyCharts(String chatId, List<HistoryResponseResult[]> listOfHistoryResponseResults,
                                                String chartName, String[] seriesName) {

        //создаем набор данных графика
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        //наполняем его значениями
        for (int i = 0; i < listOfHistoryResponseResults.size(); i++) {
            dataset = chartService.createIcmpPingDataset(dataset, listOfHistoryResponseResults.get(i), seriesName[i]);
        }

        byte[] icmpPingLineChartPicture = null;
        try {
            icmpPingLineChartPicture = chartService.createLineChartPicture(dataset, chartName);
            log.info("received byte[] from createLineChartPicture");
        } catch (IOException e) {
            log.error("could not convert byteArrayInputStream to byte array: ", e);
        }


        InputStream byteArrayInputStream = new ByteArrayInputStream(icmpPingLineChartPicture);

        SendPhoto sendPhoto = SendPhoto.builder()
                .chatId(chatId)
                .photo(new InputFile(byteArrayInputStream, "ping picture"))
                .build();

        try {
            telegramBot.execute(sendPhoto);
            log.info("sent {} to {}", chartName, chatId);
        } catch (TelegramApiException e) {
            log.error("sendHistoryPicture could not sent {} chart message with id {}", chartName, chatId, e);
        }
    }

    /**
     * Формирует и отправляет картинку с одним линейным графиком
     *
     * @param chatId                 id чата
     * @param historyResponseResults данные для формирования графика
     * @param chartName              имя графика
     * @param seriesName             подпись линии на графике
     */
    @Override
    public void sendHistoryPicture(String chatId, HistoryResponseResult[] historyResponseResults,
                                   String chartName, String seriesName) {

        //создаем набор данных графика
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        //наполняем его значениями
        dataset = chartService.createIcmpPingDataset(dataset, historyResponseResults, seriesName);

        byte[] icmpPingLineChartPicture = null;
        try {
            icmpPingLineChartPicture = chartService.createLineChartPicture(dataset, chartName);
            log.info("received byte[] from createLineChartPicture");
        } catch (IOException e) {
            log.error("could not convert byteArrayInputStream to byte array: ", e);
        }

        InputStream in = new ByteArrayInputStream(icmpPingLineChartPicture);
        SendPhoto ya_ping = SendPhoto.builder()
                .chatId(chatId)
                .photo(new InputFile(in, "ping picture"))
                .build();

        try {
            telegramBot.execute(ya_ping);
            log.info("sent {} to {}", chartName, chatId);
        } catch (TelegramApiException e) {
            log.error("sendHistoryPicture could not sent {} chart message with id {}", chartName, chatId, e);
        }
    }

    /**
     * Формирует и отправляет картинку с закрашенным линейным графиком
     *
     * @param chatId                 id чата
     * @param historyResponseResults данные для формирования графика
     * @param chartName              имя графика
     * @param seriesName             подпись линии на графике
     */
    @Override
    public void sendCpuUtilizationChart(String chatId, HistoryResponseResult[] historyResponseResults,
                                        String chartName, String seriesName) {

        //создаем набор данных графика
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        //наполняем его значениями
        dataset = chartService.createIcmpPingDataset(dataset, historyResponseResults, seriesName);

        byte[] icmpPingLineChartPicture = null;
        icmpPingLineChartPicture = chartService.createAreaChartPicture(dataset, chartName);

        InputStream in = new ByteArrayInputStream(icmpPingLineChartPicture);
        SendPhoto cpuChart = SendPhoto.builder()
                .chatId(chatId)
                .photo(new InputFile(in, "ping picture"))
                .build();

        try {
            telegramBot.execute(cpuChart);
            log.info("sent {} to {}", chartName, chatId);
        } catch (TelegramApiException e) {
            log.error("sendCpuUtilization could not sent {} message with id {}", chartName, chatId, e);
        }
    }

    /**
     * Формирует и отправляет картинку с графиком "пирог"
     *
     * @param chatId                 id чата
     * @param historyResponseResults данные для формирования графика
     * @param chartName              имя графика
     * @param seriesName             подпись линии на графике
     */
    @Override
    public void sendPiePicture(String chatId, HistoryResponseResult[] historyResponseResults,
                               String chartName, String seriesName) {

        DefaultPieDataset datasetForPieChart = chartService.createDatasetForPieChart(historyResponseResults, seriesName);

        byte[] pieChartPicture = null;
        try {
            pieChartPicture = chartService.createPieChart(datasetForPieChart, chartName);
            log.info("received byte[] from createLineChartPicture");
        } catch (IOException e) {
            log.error("could not convert byteArrayInputStream to byte array: ", e);
        }

        InputStream in = new ByteArrayInputStream(pieChartPicture);
        SendPhoto piePicture = SendPhoto.builder()
                .chatId(chatId)
                .photo(new InputFile(in, "pie picture"))
                .build();

        try {
            telegramBot.execute(piePicture);
            log.info("sent {} to {}", chartName, chatId);
        } catch (TelegramApiException e) {
            log.error("sendPiePicture could not sent {} message with id {}", chartName, chatId, e);
        }
    }

    /**
     * Удаляет нужное сообщение из чата
     *
     * @param chatId    id чата
     * @param messageId id сообщения
     */
    @Override
    public void deleteMessageFromChat(String chatId, Integer messageId) {
        DeleteMessage deleteMessage = DeleteMessage.builder()
                .chatId(chatId)
                .messageId(messageId)
                .build();
        try {
            telegramBot.execute(deleteMessage);
        } catch (TelegramApiException e) {
            log.error("deleteMessageFromChat could not delete message with id {}", messageId, e);
        }
    }

    /**
     * Редактирует сообщение и добавляет к нему inLine кнопку "Редактировать"
     * {@link SendMessageServiceImpl#formInlineEditButtonForDepartmentsIncidentMessage()}
     * @param chatId            id чата
     * @param newMessage        новый текст сообщения
     * @param originalMessageId id сообщения, которое будет отредактировано
     */
    @Override
    public void editMessageToGroupWithInlineEditButton(String chatId, String newMessage,
                                                       Integer originalMessageId) {
        InlineKeyboardMarkup editButton = formInlineEditButtonForDepartmentsIncidentMessage();

        EditMessageText editMessage = EditMessageText.builder()
                .chatId(chatId)
                .text(newMessage)
                .messageId(originalMessageId)
                .replyMarkup(editButton)
                .disableWebPagePreview(false)
                .parseMode(ParseMode.HTML)
                .build();

        try {
            telegramBot.execute(editMessage);
        } catch (TelegramApiException e) {
            log.error("sendMessageToGroupWithInlineEditButton could not edit message: {}", newMessage, e);
        }
    }

    /**
     * Отправляет сообщение и добавляет к нему inLine кнопку "Дополнить сообщение""
     * {@link SendMessageServiceImpl#formInlineEditButtonForDepartmentsIncidentMessage()}
     *
     * @param chatId id чата
     * @param message
     */
    @Override
    public void sendMessageToGroupWithInlineEditButton(String chatId, String message) {

        InlineKeyboardMarkup editButton = formInlineEditButtonForDepartmentsIncidentMessage();

        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(message)
                .parseMode(ParseMode.HTML)
                .replyMarkup(editButton)
                .build();

        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Could not sent message: {}", message, e);
        }

    }

    /**
     * Отправляет эмодзи
     * @param chatId id чата
     * @param emoji эмодзи
     */
    @Override
    public void sendEmoji(String chatId, String emoji) {
        SendDice emojiToSend = SendDice.builder()
                .chatId(chatId)
                .emoji(emoji)
                .build();

        try {
            telegramBot.execute(emojiToSend);
        } catch (TelegramApiException e) {
            log.error("Could not sent emoji for {}", chatId, e);
        }
    }

    /**
     * Отправляет уведомление в чат (реакция на нажатие inLine кнопки)
     * @param callBackQueryId id нажатия на inLine кнопку
     * @param notification уведомление
     */
    @Override
    public void sendAnswer(String callBackQueryId, String notification) {
        AnswerCallbackQuery answerCallbackQuery = AnswerCallbackQuery.builder()
                .callbackQueryId(callBackQueryId)
                .text(notification)
                .build();

        try {
            telegramBot.execute(answerCallbackQuery);
        } catch (TelegramApiException e) {
            log.error("Could not send callBackAnswer: {}", notification, e);
        }
    }

    /**
     * Формирует кнопку, которая добавляется в конце сообщения
     * @return inLine кнопку
     */
    private InlineKeyboardMarkup formInlineEditButtonForDepartmentsIncidentMessage() {
        InlineKeyboardMarkup editButton = InlineKeyboardMarkup.builder()
                .keyboardRow(new ArrayList<>(Arrays.asList(
                        InlineKeyboardButton.builder()
                                .text(PUSHPIN.get() + "Дополнить сообщение")
                                .callbackData("/update").build())))
                .build();
        return editButton;
    }
}
