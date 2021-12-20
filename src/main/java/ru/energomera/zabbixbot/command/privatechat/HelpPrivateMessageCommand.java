package ru.energomera.zabbixbot.command.privatechat;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.service.SendMessageService;

import static ru.energomera.zabbixbot.emoji.Icon.SUNGLASSES;

/**
 * Класс реализует {@link Command}
 * Команда отображает сообщение-помощь при общении с ботом в личных сообщениях
 */
public class HelpPrivateMessageCommand implements Command {
    private final SendMessageService sendMessageService;

    public static final String HELP_MESSAGE_FOR_ADMINS_GROUP =
            "Тут все максимально просто: жмакай по <b><i>/MENU</i></b>, а дальше как то разберешься "
            + SUNGLASSES.get();

    public HelpPrivateMessageCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        sendMessageService.sendMessage(chatId, HELP_MESSAGE_FOR_ADMINS_GROUP);
    }
}
