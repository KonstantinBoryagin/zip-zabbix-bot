package ru.energomera.zabbixbot.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import ru.energomera.zabbixbot.command.CommandContainer;
import ru.energomera.zabbixbot.service.SendMessageServiceImpl;

import static ru.energomera.zabbixbot.command.CommandName.*;
import static ru.energomera.zabbixbot.sticker.Icon.BACK;

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
            } else if(message.contains("Сыграем")){
                commandContainer.retrieveCommand(DICE.getCommandName()).execute(update);
            } else if(message.contains("Графики")){
                commandContainer.retrieveCommand(MENU_CHARTS.getCommandName()).execute(update);
            } else if (message.equals("ЗИП")) {
                commandContainer.retrieveCommand("ZipCommand").execute(update);
            } else if (message.equals(BACK.get() + "  Назад")) {    //вынеси это в константы классов
                commandContainer.retrieveCommand(MENU.getCommandName()).execute(update);
            } else if (message.equals(PROXY_PING_COMMAND.getCommandName())) {
                commandContainer.retrieveCommand(PROXY_PING_COMMAND.getCommandName()).execute(update);
            } else{
//                commandContainer.retrieveCommand(.getCommandName()).execute(update);
                commandContainer.retrieveCommand(TEMP2.getCommandName()).execute(update);
            }
        } else if (update.hasCallbackQuery()) {

            String message = update.getCallbackQuery().getData();

            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            }

        } else if (update.hasChannelPost()) {
            String s = update.getChannelPost().getChatId().toString();
            System.out.println(s);
//            commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
            commandContainer.retrieveCommand(CHART.getCommandName()).execute(update);

        } else if(update.hasInlineQuery()) {

            commandContainer.retrieveCommand(TEMP_INLINE.getCommandName()).execute(update);


        } else {

            Sticker sticker = update.getMessage().getSticker();
            if (sticker != null) {
                String fileId = sticker.getFileId();
                System.out.println(fileId);
            }
        }
    }
}


