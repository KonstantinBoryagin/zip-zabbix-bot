package ru.energomera.zabbixbot.command;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.energomera.zabbixbot.service.SendMessageService;

import java.util.ArrayList;
import java.util.List;

public class TempCommand implements Command{
    private final SendMessageService sendMessageService;

    public TempCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        String command = update.getCallbackQuery().getData().toLowerCase();
        String callbackQueryId = update.getCallbackQuery().getId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String oldMessage = update.getCallbackQuery().getMessage().getText();  //забирает старый текст

//        SendMessage.builder().

        List<List<InlineKeyboardButton>> keyboardList = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(InlineKeyboardButton.builder().text("hi").callbackData("/yes").build());
        row.add(InlineKeyboardButton.builder().text("bye").callbackData("/no").build());
        keyboardList.add(row);
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(keyboardList);

        EditMessageText build1 = EditMessageText.builder().chatId(chatId).text(oldMessage).messageId(messageId).replyMarkup(keyboard).build();

        switch(command) {
            case "/yes":
                AnswerCallbackQuery build = AnswerCallbackQuery.builder().callbackQueryId(callbackQueryId).text("Вы сказали ДА!").build();
                sendMessageService.sendAnswer(build);
                sendMessageService.sendTest(build1);
                break;
            case "/no":
                AnswerCallbackQuery build2 = AnswerCallbackQuery.builder().callbackQueryId(callbackQueryId).text("Вы сказали NO!").showAlert(true).build();
                sendMessageService.sendAnswer(build2);
        }


    }
}
