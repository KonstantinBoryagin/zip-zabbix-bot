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
import static ru.energomera.zabbixbot.sticker.Icon.ARROW_HEADING_DOWN;

/**
 * Класс для отображения клавиатуры выбора графиков в личных сообщениях для админов
 */
public class MenuChartsCommand implements Command {
    private final SendMessageService sendMessageService;
    private final String message = "Графики " + ARROW_HEADING_DOWN.get();

    public MenuChartsCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        KeyboardRow keyboardRow1 = new KeyboardRow(
          new ArrayList<KeyboardButton>(Arrays.asList(KeyboardButton.builder().text(CPU_SRV_ERP_2.getCommandName()).build())
        ));
        KeyboardRow keyboardRow2 = new KeyboardRow(
                new ArrayList<>(Arrays.asList(KeyboardButton.builder().text(PROXY_PING_COMMAND.getCommandName()).build(),
                        KeyboardButton.builder().text(INTERNET_PING.getCommandName()).build()))
        );
        KeyboardRow keyboardRow3 = new KeyboardRow(
                new ArrayList<KeyboardButton>(Arrays.asList(KeyboardButton.builder().text(COMMUTATOR_PING.getCommandName()).build(),
                        KeyboardButton.builder().text(ERP_DISK_F.getCommandName()).build())
                        ));
        KeyboardRow keyboardRow5 = new KeyboardRow(
                new ArrayList<KeyboardButton>(Arrays.asList(KeyboardButton.builder().text(BACK.getCommandName()).build())
                ));

        ReplyKeyboardMarkup replyKeyboardMarkup = ReplyKeyboardMarkup.builder()
                .resizeKeyboard(true)       //подогнать под размер экрана
                .oneTimeKeyboard(false)     //скрывать после нажатия
                .keyboardRow(keyboardRow1)
                .keyboardRow(keyboardRow2)
                .keyboardRow(keyboardRow3)
                .keyboardRow(keyboardRow5)
                .build();


        sendMessageService.sendPrivateMessageWithReplyKeyboardMarkup(chatId, message, replyKeyboardMarkup);

    }
}
