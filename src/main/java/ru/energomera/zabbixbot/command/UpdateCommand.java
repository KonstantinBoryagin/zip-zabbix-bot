package ru.energomera.zabbixbot.command;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.energomera.zabbixbot.service.SendMessageService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateCommand implements Command {
    private final SendMessageService sendMessageService;
    public static Map<User, List<Object>> userChoose = new HashMap<>();

    public UpdateCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {


        if (update.hasInlineQuery()) {
            String query = update.getInlineQuery().getQuery();
            System.out.println(query);
            String[] split = query.split("\\|");
            if(split.length >= 3) {
//            if(query.endsWith("|")){
                System.out.println("ends");
                String chatId = split[0];
                System.out.println(chatId);
                Integer messageId = Integer.valueOf(split[1]);
                System.out.println(messageId);
                String text = split[2];
                System.out.println(text);
                EditMessageText build = EditMessageText.builder().chatId(chatId).messageId(messageId).text(text).build();
                sendMessageService.sendTest(build);
//            }
            }

        } else if(update.hasCallbackQuery()) {
            List<Object> messagesIdForUser = new ArrayList<>();

            String oldMessage = update.getCallbackQuery().getMessage().getText() + "\n ------------------ \n";
            String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
            Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
            User user = update.getCallbackQuery().getFrom();
            String callBackQueryId = update.getCallbackQuery().getId();

            messagesIdForUser.add(oldMessage);
            messagesIdForUser.add(chatId);
            messagesIdForUser.add(messageId);

            AnswerCallbackQuery build = AnswerCallbackQuery.builder()
                    .callbackQueryId(callBackQueryId)
//                .showAlert(true)
                    .text("Введите ваше сообщение: ")
                    .build();

            sendMessageService.sendTest(build);

//            CopyMessage build1 = CopyMessage.builder().messageId(messageId).chatId(chatId).fromChatId(chatId).build();
//
//            sendMessageService.sendMessageWithReplyCopy(chatId, build1);

//            List<List<InlineKeyboardButton>> keyboardList = new ArrayList<>();
//            List<InlineKeyboardButton> row = new ArrayList<>();
//            row.add(InlineKeyboardButton.builder().text("hi").callbackData("/yes").build());
//            row.add(InlineKeyboardButton.builder().text("bye").callbackData("/no").build());
//            keyboardList.add(row);
//            List<InlineKeyboardButton> row2 = new ArrayList<>();
//            row2.add(InlineKeyboardButton.builder()
//                    .text("test")
//                    .switchInlineQueryCurrentChat(chatId + "|" + messageId.toString() + "|")
//                    .build());
//            keyboardList.add(row2);
//
//            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(keyboardList);
//            EditMessageReplyMarkup build1 = EditMessageReplyMarkup.builder().chatId(chatId).messageId(messageId)
//                    .replyMarkup(keyboard).build();
//
//            sendMessageService.sendTest(build1);

        Integer tempMessage = sendMessageService.sendMessageWithReply(chatId, "красивая подсказка ");
            messagesIdForUser.add(tempMessage);

        userChoose.put(user, messagesIdForUser);
        }
    }
}
