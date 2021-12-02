package ru.energomera.zabbixbot.command.menu;

import org.telegram.telegrambots.meta.api.methods.send.SendDice;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.service.SendMessageService;

import static ru.energomera.zabbixbot.sticker.Icon.SLOT_MACHINE;

public class DiceCommand implements Command {
    private final SendMessageService sendMessageService;

    public DiceCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        SendDice build = SendDice.builder()
                .chatId(update.getMessage().getChatId().toString())
                .emoji(SLOT_MACHINE.get())
                .build();

        build.setReplyMarkup(ReplyKeyboardMarkup.builder().clearKeyboard().build());


        sendMessageService.sendDice(build);

    }
}
