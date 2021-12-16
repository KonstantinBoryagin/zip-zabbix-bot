package ru.energomera.zabbixbot.command.privatechat.emoji;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.service.SendMessageService;

import static ru.energomera.zabbixbot.emoji.Icon.GAME_DICE;

/**
 * Класс реализует {@link Command}
 * Отправляет эмодзи (в телеграмме она анимируется)
 */
public class DiceCommand implements Command {
    private final SendMessageService sendMessageService;

    public DiceCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        sendMessageService.sendEmoji(chatId, GAME_DICE.get());

    }
}
