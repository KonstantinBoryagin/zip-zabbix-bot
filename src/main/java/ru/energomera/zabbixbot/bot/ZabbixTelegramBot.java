package ru.energomera.zabbixbot.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import ru.energomera.zabbixbot.command.CommandContainer;
import ru.energomera.zabbixbot.command.ZipCommand;
import ru.energomera.zabbixbot.service.SendMessageServiceImpl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.energomera.zabbixbot.command.CommandName.*;

@Component
public class ZabbixTelegramBot extends TelegramLongPollingBot {

    public static final String COMMAND_PREFIX = "/";

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    private final CommandContainer commandContainer;

    public ZabbixTelegramBot() {
        this.commandContainer = new CommandContainer(new SendMessageServiceImpl(this));
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();

            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else if(message.contains(CPU_SRV_ERP_2.getCommandName())) {
                commandContainer.retrieveCommand(CPU_SRV_ERP_2.getCommandName()).execute(update);
            } else if(message.contains(HISTORY.getCommandName())) {
                commandContainer.retrieveCommand(HISTORY.getCommandName()).execute(update);
            } else if (message.equals("ЗИП")) {
                commandContainer.retrieveCommand("ZipCommand").execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
            }
        } else if (update.hasCallbackQuery()) {
            String message = update.getCallbackQuery().getData();

            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
            }
        } else {
            Sticker sticker = update.getMessage().getSticker();
            if (sticker != null) {
                String fileId = sticker.getFileId();
                System.out.println(fileId);
            }
        }
    }
}


