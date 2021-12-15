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
import static ru.energomera.zabbixbot.emoji.Icon.*;

public class WeatherMenuCommand implements Command {
    private final SendMessageService sendMessageService;
    private final String message = "<b><i>Давай посмотрим   </i></b>" + ARROW_HEADING_DOWN.get();

    public WeatherMenuCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        KeyboardRow keyboardRow1 = new KeyboardRow(
                new ArrayList<>(
                        Arrays.asList(KeyboardButton.builder().text(CURRENT_WEATHER.getCommandName()).build())));
        KeyboardRow keyboardRow2 = new KeyboardRow(
                new ArrayList<>(
                        Arrays.asList(KeyboardButton.builder().text(TWO.get() + "  coming soon_").build())));
        KeyboardRow keyboardRow3 = new KeyboardRow(
                new ArrayList<>(
                        Arrays.asList(KeyboardButton.builder().text(THREE.get() + "  coming soon").build())));
        KeyboardRow keyboardRow4 = new KeyboardRow(
                new ArrayList<>(
                        Arrays.asList(KeyboardButton.builder().text(BACK.getCommandName()).build())));

        ReplyKeyboardMarkup replyKeyboardMarkup = ReplyKeyboardMarkup.builder()
                .resizeKeyboard(true)
                .oneTimeKeyboard(false)
                .keyboardRow(keyboardRow1)
                .keyboardRow(keyboardRow2)
                .keyboardRow(keyboardRow3)
                .keyboardRow(keyboardRow4)
                .build();

        sendMessageService.sendPrivateMessageWithReplyKeyboardMarkup(chatId, message, replyKeyboardMarkup);

    }
}