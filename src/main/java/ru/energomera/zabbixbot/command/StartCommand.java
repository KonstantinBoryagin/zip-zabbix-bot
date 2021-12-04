package ru.energomera.zabbixbot.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.energomera.zabbixbot.service.SendMessageService;

import java.util.ArrayList;
import java.util.List;

public class StartCommand implements Command{

    private final SendMessageService sendMessageService;

    public static final String START_MESSAGE = "Привет, это %s для мониторинга Zabbix системы. " +
            "Для того что бы получать уведомления, оформите заявку в ОИТ " +
            "<b>обязательно</b> приложив Ваш User Id - <b>%s</b>!";

    public StartCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        List<List<InlineKeyboardButton>> keyboardList = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(InlineKeyboardButton.builder().text("Send").callbackData("/send").build());
        keyboardList.add(row);

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(keyboardList);

        Integer enter_your_message = sendMessageService.sendMessage(chatId, "Enter your message");


    }
}
