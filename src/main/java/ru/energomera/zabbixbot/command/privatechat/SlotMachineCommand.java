package ru.energomera.zabbixbot.command.privatechat;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.service.SendMessageService;

import static ru.energomera.zabbixbot.sticker.Icon.SLOT_MACHINE;

/**
 * Немножко фана, анимированное эмодзи с игровым автоматом
 */
public class SlotMachineCommand implements Command {
    private final SendMessageService sendMessageService;

    public SlotMachineCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
       String chatId = update.getMessage().getChatId().toString();
       String emoji = SLOT_MACHINE.get();

        sendMessageService.sendEmoji(chatId, emoji);

    }
}
