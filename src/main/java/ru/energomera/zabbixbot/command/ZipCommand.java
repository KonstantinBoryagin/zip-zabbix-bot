package ru.energomera.zabbixbot.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.sticker.Stickers;

/**
 * Пасхалочка ;)
 */
public class ZipCommand implements Command{
    private final SendMessageService sendMessageService;

    public ZipCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        sendMessageService.sendSticker(chatId, Stickers.PIG);
    }
}
