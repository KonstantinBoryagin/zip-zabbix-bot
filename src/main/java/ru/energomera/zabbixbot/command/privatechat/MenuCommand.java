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
import static ru.energomera.zabbixbot.emoji.Icon.ARROW_HEADING_DOWN;

/**
 * Класс реализует {@link Command}
 * Отправляет главную клавиатуру (выбор доступных меню)
 */
public class MenuCommand implements Command {
    private final SendMessageService sendMessageService;
    private final String message = "<b><i>Выбирай   </i></b>" + ARROW_HEADING_DOWN.get();

    public MenuCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        KeyboardRow keyboardRow1 = new KeyboardRow(
                new ArrayList<>(
                        Arrays.asList(KeyboardButton.builder().text(PROBLEM.getCommandName()).build(),
                                KeyboardButton.builder().text(MENU_CHARTS.getCommandName() + "(не доступно)").build())));
        KeyboardRow keyboardRow2 = new KeyboardRow(
                new ArrayList<>(
                        Arrays.asList(KeyboardButton.builder().text(WEATHER.getCommandName()).build(),
                                KeyboardButton.builder().text(GAMES.getCommandName()).build())));

        ReplyKeyboardMarkup replyKeyboardMarkup = ReplyKeyboardMarkup.builder()
                .resizeKeyboard(true)
                .oneTimeKeyboard(false)
                .keyboardRow(keyboardRow1)
                .keyboardRow(keyboardRow2)
                .build();

        sendMessageService.sendPrivateMessageWithReplyKeyboardMarkup(chatId, message, replyKeyboardMarkup);

    }
}