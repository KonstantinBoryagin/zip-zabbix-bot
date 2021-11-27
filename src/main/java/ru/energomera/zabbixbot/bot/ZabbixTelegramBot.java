package ru.energomera.zabbixbot.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Locale;

@Component
public class ZabbixTelegramBot extends TelegramLongPollingBot {

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

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
        if(update.hasMessage()){

            if(update.getMessage().hasText()){
                SendMessage send = new SendMessage();
                send.setText("ты прислал - " + update.getMessage().getText().toUpperCase(Locale.ROOT));
                send.setChatId(update.getMessage().getChatId().toString());
                try {
                    execute(send);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
