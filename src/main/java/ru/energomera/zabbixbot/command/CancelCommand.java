package ru.energomera.zabbixbot.command;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.energomera.zabbixbot.service.SendMessageService;

import java.util.List;

import static ru.energomera.zabbixbot.command.UpdateCommand.userChoose;

public class CancelCommand implements Command{
    private final SendMessageService sendMessageService;

    public CancelCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        Integer thisMessageId = update.getMessage().getMessageId();
        String chatId = update.getMessage().getChatId().toString();
        User user = update.getMessage().getFrom();

        if (userChoose.containsKey(user)) {
            List<Object> userList = userChoose.get(user);

            Integer warningMessageId = (Integer) userList.get(4);
            Integer helpMessageId = (Integer) userList.get(3);
            String userChatId = (String) userList.get(1);

            if (userChatId.equals(chatId)) {

                //DELETE MESSAGES
                DeleteMessage delete1 = DeleteMessage.builder()
                        .chatId(userChatId)
                        .messageId(thisMessageId)
                        .build();

                sendMessageService.sendTest(delete1);

                DeleteMessage delete2 = DeleteMessage.builder()
                        .chatId(userChatId)
                        .messageId(warningMessageId)
                        .build();

                sendMessageService.sendTest(delete2);

                DeleteMessage delete3 = DeleteMessage.builder()
                        .chatId(userChatId)
                        .messageId(helpMessageId)
                        .build();

                sendMessageService.sendTest(delete3);

                    ////чистим мапу
                userChoose.remove(user);
            }
        }
    }
}
