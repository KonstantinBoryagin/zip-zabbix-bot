package ru.energomera.zabbixbot.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.emoji.Icon;

import static ru.energomera.zabbixbot.command.CommandName.HELP;

public class UnknownCommand implements Command{
    private final SendMessageService sendMessageService;

    public static final String UNKNOWN_MESSAGE = String.format(Icon.NOT.get() + "Не понимаю тебя!" + Icon.NOT.get() + "\n" +
                                "Попробуй обратиться за помощью - %s", HELP.getCommandName());

    public UnknownCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {

        String chatId = update.getMessage().getChatId().toString();
        Integer messageId = update.getMessage().getMessageId();

        sendMessageService.sendReplyMessage(chatId, UNKNOWN_MESSAGE, messageId);
    }
}
