package ru.energomera.zabbixbot.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.energomera.zabbixbot.service.SendMessageService;

import java.util.ArrayList;
import java.util.List;

import static ru.energomera.zabbixbot.command.TempInlineCommand.userPrivateChoose;
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


            String newMessage = oldMessage + signature + ": \n" +  update.getMessage().getText();

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

                DeleteMessage delete2 = DeleteMessage.builder()
                        .chatId(userChatId)
                        .messageId(thisMessageId)
                        .build();
                System.out.println(thisMessageId);

                sendMessageService.sendTest(delete2);

                DeleteMessage delete1 = DeleteMessage.builder()
                        .chatId(userChatId)
                        .messageId(helpMesssageId)
                        .build();

                sendMessageService.sendTest(delete1);



                userChoose.remove(user);
            }
        }

        if(userPrivateChoose.containsKey(user)){
            List<String> postData = userPrivateChoose.get(user);

            String oldText = postData.get(0);
            String groupChatId = postData.get(1);
            Integer targetMessageId = Integer.valueOf(postData.get(2));

            String firstname = user.getFirstName();
            String lastname = user.getLastName();
            String signature = lastname == null ? firstname : firstname + " " + lastname;

            String newMessage = oldText +
                    "\n ---------------------------------------------------------- \n"
                    + PUSHPIN.get() + "    " + signature + ": \n" +  update.getMessage().getText();

            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(addInlineKeyboardToGroupNotificationPost());

            EditMessageText editMessage = EditMessageText.builder()
                    .chatId(groupChatId)
                    .text(newMessage)
                    .messageId(targetMessageId)
                    .replyMarkup(keyboard)
                    .disableWebPagePreview(false)
                    .build();

            sendMessageService.sendTest(editMessage);

            userPrivateChoose.remove(user);

            //////////////////// BACK user to group

            /////////////////////////////////////////////
        List<List<InlineKeyboardButton>> keyboardList = new ArrayList<>();

        ////////////////////////
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(InlineKeyboardButton.builder().text(PUSHPIN.get() + "Switch").switchInlineQuery("").build());
        //////////////////////

        /////////////
        keyboardList.add(row2);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup(keyboardList);


        sendMessageService.sendTest(SendMessage.builder()
                .chatId(chatId)
                .text("Go")
                .replyMarkup(keyboardMarkup)
                .build());
        }


    }
}
