package ru.energomera.zabbixbot.command;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.energomera.zabbixbot.service.SendMessageService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.energomera.zabbixbot.sticker.Icon.PUSHPIN;
import static ru.energomera.zabbixbot.sticker.Icon.WHITE_CHECK_MARK;

public class UpdateCommand implements Command {
    private final SendMessageService sendMessageService;
    public static Map<User, List<Object>> userChoose = new HashMap<>();

    public UpdateCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {

//
//        if (update.hasInlineQuery()) {
//            String query = update.getInlineQuery().getQuery();
//            System.out.println(query);
//            String[] split = query.split("\\|");
//            if(split.length >= 3) {
////            if(query.endsWith("|")){
//                System.out.println("ends");
//                String chatId = split[0];
//                System.out.println(chatId);
//                Integer messageId = Integer.valueOf(split[1]);
//                System.out.println(messageId);
//                String text = split[2];
//                System.out.println(text);
//                EditMessageText build = EditMessageText.builder().chatId(chatId).messageId(messageId).text(text).build();
//                sendMessageService.sendTest(build);
////            }
//            }
//
//        } else if(update.hasCallbackQuery()) {
        Integer subCommand = Integer.valueOf(update.getCallbackQuery().getData().split("\\|")[1]);

        List<Object> messagesIdForUser = new ArrayList<>();

        String oldMessage;
        if (subCommand == 1) {
            oldMessage = update.getCallbackQuery().getMessage().getText()
                    + "\n ------------------------------------------------------ \n"
                    + PUSHPIN.get() + "    "
            ;
            System.out.println(subCommand);
        } else if (subCommand == 2) {
            oldMessage = update.getCallbackQuery().getMessage().getText()
                    + "\n ------------------------------------------------------ \n"
                    + WHITE_CHECK_MARK.get() + "    "
            ;
            System.out.println(subCommand);
        } else {
            oldMessage = update.getCallbackQuery().getMessage().getText() +
                    "\n ------------------------------------------------------ \n"
            ;
        }
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

        Integer tempMessage = sendMessageService.sendMessageWithReply(chatId, "красивая подсказка ");
        messagesIdForUser.add(tempMessage);

        userChoose.put(user, messagesIdForUser);

        for(User name: userChoose.keySet()){
            System.out.println(name + "  ---  userChoose.keySet()");
        }
    }
}
