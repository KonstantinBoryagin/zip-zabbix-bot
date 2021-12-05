package ru.energomera.zabbixbot.command;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.energomera.zabbixbot.service.SendMessageService;

import java.util.List;

import static ru.energomera.zabbixbot.command.UpdateCommand.userChoose;
import static ru.energomera.zabbixbot.service.MessageFromWebHookHandler.addInlineKeyboardToGroupNotificationPost;
import static ru.energomera.zabbixbot.sticker.Icon.PUSHPIN;

public class Temp2Command implements Command{
    private final SendMessageService sendMessageService;

    public Temp2Command(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
//        System.out.println("start working temp2");
//        String chatId = update.getMessage().getChatId().toString();
//        Integer messageId = update.getMessage().getMessageId();
////        String oldMessage = update.getCallbackQuery().getMessage().getText() + "\n ------------- \n";  //забирает старый текст
//
//        List<List<InlineKeyboardButton>> keyboardList = new ArrayList<>();
//        List<InlineKeyboardButton> row = new ArrayList<>();
//        row.add(InlineKeyboardButton.builder().text("hi").callbackData("/yes").build());
//        row.add(InlineKeyboardButton.builder().text("bye").callbackData("/no").build());
//        keyboardList.add(row);
//        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(keyboardList);
//
//        Integer forwardFromMessageId = update.getMessage().getForwardFromMessageId();
//        System.out.println(forwardFromMessageId + " forwardFromMessageId");
//
//        Integer messageId1 = update.getMessage().getMessageId();
//        System.out.println(messageId1 + "   messageId1");
//
//
//
////        if(update.getMessage().getForwardFromMessageId() == Temp3Command.currentMessageId) {
//            String text = update.getMessage().getText();
//            String newText = TempCommand.oldMessage + "\n" + text;
//            System.out.println(newText);   //temp
//            EditMessageText build1 = EditMessageText.builder().chatId(chatId).text(newText).messageId(TempCommand.changedMessageId).replyMarkup(keyboard).build();
//            sendMessageService.sendTest(build1);
//        } else {
//            EditMessageText build1 = EditMessageText.builder().chatId(chatId).text("не вышло").messageId(TempCommand.changedMessageId).replyMarkup(keyboard).build();
//            sendMessageService.sendTest(build1);
//        }


//        SendMessage.builder().


        Integer thisMessageId = update.getMessage().getMessageId();
        String chatId = update.getMessage().getChatId().toString();
        User user = update.getMessage().getFrom();


        if(userChoose.containsKey(user)) {
            List<Object> userList = userChoose.get(user);

            Integer helpMesssageId = (Integer)userList.get(3);
            Integer originalMessageId = (Integer) userList.get(2);
            String userChatId = (String) userList.get(1);
            String oldMessage = (String) userList.get(0);

            String firstname = user.getFirstName();
            String lastname = user.getLastName();
            String signature = lastname == null ? firstname : firstname + " " + lastname;


            String newMessage = oldMessage + PUSHPIN.get() + "    " + signature + ": \n" +  update.getMessage().getText();

            if (userChatId.equals(chatId)) {

                InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(addInlineKeyboardToGroupNotificationPost());

                EditMessageText editMessage = EditMessageText.builder()
                        .chatId(userChatId)
                        .text(newMessage)
                        .messageId(originalMessageId)
                        .replyMarkup(keyboard)
                        .disableWebPagePreview(false)
                        .build();

                sendMessageService.sendTest(editMessage);

                //DELETE MESSAGES

                DeleteMessage delete1 = DeleteMessage.builder()
                        .chatId(userChatId)
                        .messageId(helpMesssageId)
                        .build();

                sendMessageService.sendTest(delete1);

                DeleteMessage delete2 = DeleteMessage.builder()
                        .chatId(userChatId)
                        .messageId(thisMessageId)
                        .build();

                sendMessageService.sendTest(delete2);

                userChoose.remove(user);
            }
        }


    }
}
