package ru.energomera.zabbixbot.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.service.SendMessageService;

import static ru.energomera.zabbixbot.command.CommandName.*;

public class HelpCommand implements Command{

    private final SendMessageService sendMessageService;

    public static final String HELP_MESSAGE = String.format("✨<b>Дотупные команды</b>✨\n\n"

                    + "<b>Начать\\закончить работу с ботом</b>\n"
                    + "%s - начать работу со мной\n"
                    + "%s - приостановить работу со мной\n\n"
                    + "%s - получить помощь в работе со мной\n",
            START.getCommandName(), STOP.getCommandName(), HELP.getCommandName());

    public HelpCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        sendMessageService.sendMessage(chatId, HELP_MESSAGE);
    }
}
