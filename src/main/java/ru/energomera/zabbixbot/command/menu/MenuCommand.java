package ru.energomera.zabbixbot.command.menu;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.service.SendMessageService;

import java.util.ArrayList;
import java.util.List;

import static ru.energomera.zabbixbot.sticker.Icon.CHART_IMG;
import static ru.energomera.zabbixbot.sticker.Icon.GAME_DICE;

public class MenuCommand implements Command {
    private final SendMessageService sendMessageService;

    public MenuCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        int messageId = update.getMessage().getMessageId();  //что бы у остальных не отображалась клава

        List<KeyboardButton> row1 = new ArrayList<>();
        row1.add(KeyboardButton.builder().text(CHART_IMG.get() + "  Графики").build());
        List<KeyboardButton> row2 = new ArrayList<>();
        row2.add(KeyboardButton.builder().text(GAME_DICE.get() + "  Сыграем?").build());

        KeyboardRow keyboardRow1 = new KeyboardRow(row1);
        KeyboardRow keyboardRow2 = new KeyboardRow(row2);


        ReplyKeyboardMarkup replyKeyboardMarkup = ReplyKeyboardMarkup.builder()
                .resizeKeyboard(true)       //подогнать под размер экрана
                .selective(true)             //только у пользователя которому отвечаем
                .oneTimeKeyboard(false)     //скрывать после нажатия
                .keyboardRow(keyboardRow1)
                .keyboardRow(keyboardRow2)
                .build();


        sendMessageService.sendMessageToGroupWithReplyKeyboardMarkup(chatId, "Выберите график: ", replyKeyboardMarkup, messageId);

    }
}
