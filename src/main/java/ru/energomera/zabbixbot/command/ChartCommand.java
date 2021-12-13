package ru.energomera.zabbixbot.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.energomera.zabbixbot.service.SendMessageService;

import java.util.ArrayList;
import java.util.List;

import static ru.energomera.zabbixbot.command.CommandName.*;
import static ru.energomera.zabbixbot.sticker.Icon.*;

public class ChartCommand implements Command{
    private final SendMessageService sendMessageService;

    public ChartCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();


        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardButton button = new KeyboardButton(ONE.get() + "  " + CPU_SRV_ERP_2.getCommandName());
        KeyboardButton button2 = new KeyboardButton(TWO.get() + "  " + PROXY_PING_COMMAND.getCommandName());

        List<KeyboardButton> keyboardRow1 = new ArrayList<>();
        keyboardRow1.add(button);
        List<KeyboardButton> keyboardRow2 = new ArrayList<>();
        keyboardRow2.add(button2);

        KeyboardRow row = new KeyboardRow(keyboardRow1);
        KeyboardRow row2 = new KeyboardRow(keyboardRow2);

        List<KeyboardRow> rows = new ArrayList<>();
        rows.add(row);
        rows.add(row2);

        keyboardMarkup.setKeyboard(rows);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);
        keyboardMarkup.setSelective(true);


        int messageId = update.getMessage().getMessageId();
        sendMessageService.sendMessageToGroupWithReplyKeyboardMarkup(chatId, "Выберите график: ", keyboardMarkup, messageId);

    }

    public static ReplyKeyboard replyChartOptions() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowList= new ArrayList<>();  // массив всех кнопок


        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(CHART_IMG.get() + " Меню графиков " + CHART_IMG.get());
        inlineKeyboardButton.setCallbackData(CHART.getCommandName());

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton);


        rowList.add(keyboardButtonsRow1);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;

    }

    public static ReplyKeyboard replyChartOptions2(String chartName) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowList= new ArrayList<>();  // массив всех кнопок


        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(CHART_IMG.get() + " Показать график " + CHART_IMG.get());
        inlineKeyboardButton.setCallbackData(UPDATE.getCommandName() + chartName);

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton);


        rowList.add(keyboardButtonsRow1);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;

    }
}
