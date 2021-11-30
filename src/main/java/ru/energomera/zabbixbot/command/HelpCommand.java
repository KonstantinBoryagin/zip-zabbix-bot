package ru.energomera.zabbixbot.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.sticker.Icon;

import static ru.energomera.zabbixbot.command.CommandName.*;

public class HelpCommand implements Command{

    private final SendMessageService sendMessageService;

    public static final String HELP_MESSAGE = String.format(Icon.CHECK.get() + "<b>Дотупные команды</b> " + Icon.CHECK.get()+ "\n\n"

                    + "<b>Начать\\закончить работу с ботом</b>\n"
                    + "%s - начать работу со мной\n"
                    + "%s - запросить график\n\n"
                    + "%s - получить помощь\n",
            START.getCommandName(), CHART.getCommandName(), HELP.getCommandName());

    public HelpCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        sendMessageService.sendMessage(chatId, HELP_MESSAGE);
    }
}
