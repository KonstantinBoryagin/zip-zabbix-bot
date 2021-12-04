package ru.energomera.zabbixbot.command;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.energomera.zabbixbot.service.SendMessageService;

import java.util.ArrayList;
import java.util.List;

public class TempCommand implements Command{
    private final SendMessageService sendMessageService;

    public static Integer changedMessageId = 0;
    public static String oldMessage = "";

    public TempCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        String command = update.getCallbackQuery().getData().toLowerCase();
        String callbackQueryId = update.getCallbackQuery().getId();
        changedMessageId = update.getCallbackQuery().getMessage().getMessageId();
        oldMessage = update.getCallbackQuery().getMessage().getText() + "\n ------------- \n";  //забирает старый текст

//        SendMessage.builder().

//        List<List<InlineKeyboardButton>> keyboardList = new ArrayList<>();
//        List<InlineKeyboardButton> row = new ArrayList<>();
//        row.add(InlineKeyboardButton.builder().text("hi").callbackData("/yes").build());
//        row.add(InlineKeyboardButton.builder().text("bye").callbackData("/no").build());
//        keyboardList.add(row);
//        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(keyboardList);



        switch(command) {
            case "/yes":
                AnswerCallbackQuery build = AnswerCallbackQuery.builder().callbackQueryId(callbackQueryId).text("Вы сказали ДА!").build();
                sendMessageService.sendAnswer(build);



                Temp3Command temp = new Temp3Command(sendMessageService);
                temp.execute(update);
                System.out.println("temp mesId - " + update.getCallbackQuery().getMessage().getMessageId());

//                        DeleteMessage build3 = DeleteMessage.builder().chatId(chatId).messageId(changedMessageId).build();
//
//        sendMessageService.sendTest(build3);



                break;
            case "/no":
                AnswerCallbackQuery build2 = AnswerCallbackQuery.builder().callbackQueryId(callbackQueryId).text("Вы сказали NO!").showAlert(false).build();
                System.out.println("temp mesId - " + update.getCallbackQuery().getMessage().getMessageId());
                sendMessageService.sendAnswer(build2);
                Integer messageId = update.getCallbackQuery().getMessage().getMessageId();

                List<List<InlineKeyboardButton>> keyboardList = new ArrayList<>();
                List<InlineKeyboardButton> row = new ArrayList<>();
                row.add(InlineKeyboardButton.builder().text("hi").callbackData("/yes").build());
                row.add(InlineKeyboardButton.builder().text("bye").callbackData("/no").build());
                keyboardList.add(row);
                List<InlineKeyboardButton> row2 = new ArrayList<>();
                row2.add(InlineKeyboardButton.builder().text("test").switchInlineQueryCurrentChat(messageId.toString()).build());
                keyboardList.add(row2);

                InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(keyboardList);


                EditMessageReplyMarkup build1 = EditMessageReplyMarkup.builder().chatId(update.getCallbackQuery().getMessage().getChatId().toString())
                        .messageId(update.getCallbackQuery().getMessage().getMessageId()).replyMarkup(keyboard).build();
                sendMessageService.sendTest(build1);

        }


    }

}
