package ru.energomera.zabbixbot.command.privatechat;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.service.SendMessageService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.energomera.zabbixbot.command.CommandName.*;
import static ru.energomera.zabbixbot.icon.Icon.ARROW_HEADING_DOWN;
import static ru.energomera.zabbixbot.icon.Icon.SUNGLASSES;

/**
 * Класс реализует {@link Command}
 * По запросам трудящихся
 */
public class GamesMenuCommand implements Command {
    private final SendMessageService sendMessageService;
    private final String message = SUNGLASSES.get() + "  <b><i>И пошло оно, развлекайся!   </i></b>" + ARROW_HEADING_DOWN.get();

    public GamesMenuCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        KeyboardRow keyboardRow1 = new KeyboardRow(
                new ArrayList<>(
                        Arrays.asList(KeyboardButton.builder().text(SLOT_MACHINE_COMMAND.getCommandName()).build(),
                                KeyboardButton.builder().text(DARTS_COMMAND.getCommandName()).build(),
                                KeyboardButton.builder().text(BASKETBALL_COMMAND.getCommandName()).build())));
        KeyboardRow keyboardRow2 = new KeyboardRow(
                new ArrayList<>(
                        Arrays.asList(KeyboardButton.builder().text(BOWLING_COMMAND.getCommandName()).build(),
                                KeyboardButton.builder().text(DICE_COMMAND.getCommandName()).build(),
                                KeyboardButton.builder().text(SOCCER_COMMAND.getCommandName()).build())));
        KeyboardRow keyboardRow3 = new KeyboardRow(
                new ArrayList<>(List.of(
                        KeyboardButton.builder().text(BACK.getCommandName()).build())));

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
