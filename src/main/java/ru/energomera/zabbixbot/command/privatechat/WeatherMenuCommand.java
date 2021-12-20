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
import static ru.energomera.zabbixbot.icon.Icon.*;

/**
 * Класс реализует {@link Command}
 * Формирует и отправляет клавиатуру-меню для выбора прогнозов погоды
 */
public class WeatherMenuCommand implements Command {
    private final SendMessageService sendMessageService;
    private final String message = ROBOT_FACE.get() + "  <b><i>Ну, сейчас навангую   </i></b>" + ARROW_HEADING_DOWN.get();

    public WeatherMenuCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        KeyboardRow keyboardRow1 = new KeyboardRow(
                new ArrayList<>(
                        Arrays.asList(KeyboardButton.builder().text(CURRENT_WEATHER.getCommandName()).build(),
                                KeyboardButton.builder().text(DAILY_WEATHER.getCommandName()).build())));
        KeyboardRow keyboardRow2 = new KeyboardRow(
                new ArrayList<>(
                        Arrays.asList(KeyboardButton.builder().text(WEEKLY_WEATHER.getCommandName()).build())));
        KeyboardRow keyboardRow3 = new KeyboardRow(
                new ArrayList<>(
                        Arrays.asList(KeyboardButton.builder().text(BACK.getCommandName()).build())));

        ReplyKeyboardMarkup replyKeyboardMarkup = ReplyKeyboardMarkup.builder()
                .resizeKeyboard(true)
                .oneTimeKeyboard(false)
                .keyboardRow(keyboardRow1)
                .keyboardRow(keyboardRow2)
                .keyboardRow(keyboardRow3)
                .build();

        sendMessageService.sendPrivateMessageWithReplyKeyboardMarkup(chatId, message, replyKeyboardMarkup);

    }
}
