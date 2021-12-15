package ru.energomera.zabbixbot.command.adminsgroup;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.service.SendMessageService;

import static ru.energomera.zabbixbot.emoji.Icon.*;
import static ru.energomera.zabbixbot.emoji.Icon.ROBOT_FACE;

public class HelpAdminsGroupCommand implements Command {
    private final SendMessageService sendMessageService;


    public static final String HELP_MESSAGE_FOR_ADMINS_GROUP = BULB.get() + "  <b><i>Tip:</i></b>" + "\nВ этой группе можно видеть сообщения от <b>Zabbix</b>." +
            " <i>Удивительно, не правда ли?</i>"+ FACE_WITH_MONOCLE.get() +
            "\nВ случае исправления проблемы заголовок сообщения меняется на <b>Решено</b>, а внизу появляется ее продолжительность. \n\n" +
            THOUGHT_BALLOON.get() + "<i>Для членов этой группы есть возможность писать мне в личные сообщения, попробуй!</i> "
            + ROBOT_FACE.get();

    public HelpAdminsGroupCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        sendMessageService.sendMessage(chatId, HELP_MESSAGE_FOR_ADMINS_GROUP);
    }
}
