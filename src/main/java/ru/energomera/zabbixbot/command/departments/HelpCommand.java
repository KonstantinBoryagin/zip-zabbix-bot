package ru.energomera.zabbixbot.command.departments;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.service.SendMessageService;

import static ru.energomera.zabbixbot.icon.Icon.*;

/**
 * Класс реализует {@link Command}
 * Ответ на команду /help для 3-х групп рассылки цеховых инцидентов
 */
public class HelpCommand implements Command {

    private final SendMessageService sendMessageService;

    public static final String HELP_MESSAGE = BULB.get() + " <b>Руководство по использованию</b> " + BULB.get()+ "\n\n"

                   + "Если вы хотите оставить комментарий/вопрос/прочее к конкретному " +
            "сообщению - нажмите <b>\"" + PUSHPIN.get() + "Дополнить сообщение\"</b> под ним и следуйте подсказкам. " +
            "После отправки комментария все служебные сообщения удалятся, а в группу придет уведомление об обновлении сообщения " +
            "<i>(для быстрого поиска воспользуйтесь хэштегом)</i>.\n\n"
            + THOUGHT_BALLOON.get() + "<b>Огромная просьба:</b> <i>из-за специфики моей работы, если вы передумали оставлять сообщение сейчас, воспользуйтесь " +
            "кликабельной ссылкой <b>\"CANCEL\"</b> (не промахнетесь) в подсказках, тогда я удалю лишний спам из группы и в будущем не перезапишется чей то комментарий." +
            " Good luck!  </i>" + ROBOT_FACE.get();

    public HelpCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        sendMessageService.sendMessage(chatId, HELP_MESSAGE);
    }
}
