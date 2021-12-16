package ru.energomera.zabbixbot.command.departments;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.service.SendMessageService;

import java.util.List;

import static ru.energomera.zabbixbot.command.departments.UpdateCommand.userChoose;

/**
 * Класс реализует {@link Command}
 * Реакция на нажатие /cancel при отмене редактирования сообщения об инциденте в одной из цеховых групп.
 * Очищает групповой чат от служебных сообщений
 */
public class CancelCommand implements Command {
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

                sendMessageService.deleteMessageFromChat(chatId, thisMessageId);
                sendMessageService.deleteMessageFromChat(chatId, warningMessageId);
                sendMessageService.deleteMessageFromChat(chatId, helpMessageId);

                //чистим мапу
                userChoose.remove(user);
            }
        }
    }
}
