package ru.energomera.zabbixbot.command;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.energomera.zabbixbot.service.SendMessageService;

import java.util.ArrayList;
import java.util.List;

public class Temp2Command implements Command{
    private final SendMessageService sendMessageService;

    public Temp2Command(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        System.out.println("start working temp2");
        String chatId = update.getMessage().getChatId().toString();
        Integer messageId = update.getMessage().getMessageId();
//        String oldMessage = update.getCallbackQuery().getMessage().getText() + "\n ------------- \n";  //забирает старый текст

        List<List<InlineKeyboardButton>> keyboardList = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(InlineKeyboardButton.builder().text("hi").callbackData("/yes").build());
        row.add(InlineKeyboardButton.builder().text("bye").callbackData("/no").build());
        keyboardList.add(row);
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(keyboardList);

        Integer forwardFromMessageId = update.getMessage().getForwardFromMessageId();
        System.out.println(forwardFromMessageId + " forwardFromMessageId");

        Integer messageId1 = update.getMessage().getMessageId();
        System.out.println(messageId1 + "   messageId1");



//        if(update.getMessage().getForwardFromMessageId() == Temp3Command.currentMessageId) {
            String text = update.getMessage().getText();
            String newText = TempCommand.oldMessage + "\n" + text;
            System.out.println(newText);   //temp
            EditMessageText build1 = EditMessageText.builder().chatId(chatId).text(newText).messageId(TempCommand.changedMessageId).replyMarkup(keyboard).build();
            sendMessageService.sendTest(build1);
//        } else {
//            EditMessageText build1 = EditMessageText.builder().chatId(chatId).text("не вышло").messageId(TempCommand.changedMessageId).replyMarkup(keyboard).build();
//            sendMessageService.sendTest(build1);
//        }


//        SendMessage.builder().




    }
}
