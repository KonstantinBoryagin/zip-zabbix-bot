package ru.energomera.zabbixbot.command.privatechat;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.service.SendMessageService;

import static ru.energomera.zabbixbot.emoji.Icon.MONKEY;
import static ru.energomera.zabbixbot.emoji.Icon.ROBOT_FACE;

/**
 * Класс реализует {@link Command}
 * Ответ на команду /start в приватном чате с ботом
 * (увидят только участники группы администраторов)
 */
public class StartCommand implements Command {

    private final SendMessageService sendMessageService;

    public static final String START_MESSAGE = "Привет %s, это " + ROBOT_FACE.get() +
            " для мониторинга Zabbix системы. \n\n" + "В этом чате тебе всего лишь  нужно нажать на <b><i>/menu</i></b>, "
            + "а дальше разберется даже " + MONKEY.get();

    public StartCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        User user = update.getMessage().getFrom();
        String chatId = update.getMessage().getChatId().toString();
        String signature = user.getLastName() == null ? user.getFirstName() : user.getFirstName() + " " + user.getLastName();
        String message = String.format(START_MESSAGE, signature);

        sendMessageService.sendMessage(chatId, message);
    }
}
