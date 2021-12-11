package ru.energomera.zabbixbot.command.privatechat;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.service.SendMessageService;

import java.util.ArrayList;
import java.util.Arrays;

import static ru.energomera.zabbixbot.command.CommandName.*;

public class MenuCommand implements Command {
    private final SendMessageService sendMessageService;

    public MenuCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        int messageId = update.getMessage().getMessageId();  //что бы у остальных не отображалась клава

        KeyboardRow keyboardRow1 = new KeyboardRow(
                new ArrayList<>(
                        Arrays.asList(KeyboardButton.builder().text(PROBLEM.getCommandName()).build())));
        KeyboardRow keyboardRow2 = new KeyboardRow(
                new ArrayList<>(
                        Arrays.asList(KeyboardButton.builder().text(MENU_CHARTS.getCommandName()).build())));
        KeyboardRow keyboardRow3 = new KeyboardRow(
                new ArrayList<>(
                        Arrays.asList(KeyboardButton.builder().text(SLOT.getCommandName()).build())));

        ReplyKeyboardMarkup replyKeyboardMarkup = ReplyKeyboardMarkup.builder()
                .resizeKeyboard(true)       //подогнать под размер экрана
                .selective(true)             //только у пользователя которому отвечаем
                .oneTimeKeyboard(false)     //скрывать после нажатия
                .keyboardRow(keyboardRow1)
                .keyboardRow(keyboardRow2)
                .keyboardRow(keyboardRow3)
                .build();

        sendMessageService.sendMessageToGroupWithReplyKeyboardMarkup(chatId, "Выберите график: ", replyKeyboardMarkup, messageId);

    }
}