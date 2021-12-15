package ru.energomera.zabbixbot.command.departments;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.service.SendMessageService;

import java.util.List;

import static ru.energomera.zabbixbot.command.departments.UpdateCommand.userChoose;
import static ru.energomera.zabbixbot.emoji.Icon.MAILBOX_WITH_MAIL;
import static ru.energomera.zabbixbot.emoji.Icon.PUSHPIN;

/**
 * Класс для изменения сообщения об инциденте в одной из цеховых групп.
 * Добавляет полученный от пользователя комментарий к сообщению инцидента, удаляет весь спам оставшийся
 * после этого и оправляет в группу уведомление о новом комментарии (с хэштегом для быстрого поиска),
 * так как телеграм не уведомляет при изменении текста.
 */
@Slf4j
public class EditIncidentMessageCommand implements Command {
    private final SendMessageService sendMessageService;
    private final String notificationText = MAILBOX_WITH_MAIL.get() + "<i>   %s оставил(а) новый комментарий в %s</i>";

    public EditIncidentMessageCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {

        Integer thisMessageId = update.getMessage().getMessageId();
        String chatId = update.getMessage().getChatId().toString();
        User user = update.getMessage().getFrom();


        if (userChoose.containsKey(user)) {
            List<Object> userList = userChoose.get(user);

            String hashtag = (String) userList.get(5);
            Integer warningMessageId = (Integer) userList.get(4);
            Integer helpMessageId = (Integer) userList.get(3);
            Integer originalMessageId = (Integer) userList.get(2);
            String userChatId = (String) userList.get(1);
            String oldMessage = (String) userList.get(0);

            String firstname = user.getFirstName();
            String lastname = user.getLastName();
            String signature = lastname == null ? firstname : firstname + " " + lastname;


            String newMessage = oldMessage + "\n ------------------------------------------------------ \n"
                    + PUSHPIN.get() + "    "
                    + signature + ": \n"
                    + update.getMessage().getText();

            if (userChatId.equals(chatId)) {
                sendMessageService.editMessageToGroupWithInlineEditButton(userChatId, newMessage, originalMessageId);

                //DELETE SERVICE MESSAGES
                sendMessageService.deleteMessageFromChat(userChatId, thisMessageId);

                sendMessageService.deleteMessageFromChat(userChatId, helpMessageId);

                sendMessageService.deleteMessageFromChat(userChatId, warningMessageId);

                // send notification about new commit
                String notificationAboutNewCommit = String.format(notificationText, signature, hashtag);

                sendMessageService.sendMessage(chatId, notificationAboutNewCommit);

                //clear map
                userChoose.remove(user);
            }
        }
    }
}
