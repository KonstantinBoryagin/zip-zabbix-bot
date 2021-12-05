package ru.energomera.zabbixbot.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.energomera.zabbixbot.service.SendMessageService;

import java.util.Arrays;

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
        User user = update.getMessage().getFrom();

//        List<List<InlineKeyboardButton>> keyboardList = new ArrayList<>();
//        List<InlineKeyboardButton> row = new ArrayList<>();
//        row.add(InlineKeyboardButton.builder().text("Send").callbackData("/send").build());
//        keyboardList.add(row);
//
//        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(keyboardList);
//
//        Integer enter_your_message = sendMessageService.sendMessage(chatId, "Enter your message");

        sendMessageService.sendMessage(chatId, chatId);

        InlineKeyboardButton build = InlineKeyboardButton.builder()
                .text("Let's begin")
                .callbackData("/change|2")
                .build();

        InlineKeyboardButton back = InlineKeyboardButton.builder().text("Back").callbackData("/back|2").build();


        InlineKeyboardMarkup build1 = InlineKeyboardMarkup.builder()
                .keyboardRow(Arrays.asList(build))
                .keyboardRow(Arrays.asList(back))
                .build();



        sendMessageService.sendTest(SendMessage.builder().replyMarkup(build1).text("Let's begin?").chatId(chatId).build());


//        /////////////////////////////////////////////
//        List<List<InlineKeyboardButton>> keyboardList = new ArrayList<>();
//
//        ////////////////////////
//        List<InlineKeyboardButton> row2 = new ArrayList<>();
//        row2.add(InlineKeyboardButton.builder().text(PUSHPIN.get() + "Switch").switchInlineQuery("").build());
//        //////////////////////
//
//        /////////////
//        keyboardList.add(row2);
//
//        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup(keyboardList);
//
//
//        sendMessageService.sendTest(SendMessage.builder()
//                .chatId(chatId)
//                .text("Go")
//                .replyMarkup(keyboardMarkup)
//                .build());
    }
}
