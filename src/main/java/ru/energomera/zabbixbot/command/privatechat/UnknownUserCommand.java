package ru.energomera.zabbixbot.command.privatechat;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.service.SendMessageService;

import static ru.energomera.zabbixbot.sticker.Icon.NO_ENTRY;

/**
 * Класс служит для ответа пользователям, не состоящим в группе администраторов и пытающихся
 * обратиться к боту в личных сообщениях
 */
public class UnknownUserCommand implements Command {
    private final SendMessageService sendMessageService;

    public static final String UNKNOWN_USER_MESSAGE = NO_ENTRY.get() + "  <b>Стоп, я тебя не знаю!</b>\n\n"
            + "<i>Что бы получить доступ к графикам и прочим ништякам необходимо " +
            "быть участником группы администраторов</i> \n\n";

    public UnknownUserCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        sendMessageService.sendMessage(chatId, UNKNOWN_USER_MESSAGE);
    }
}
