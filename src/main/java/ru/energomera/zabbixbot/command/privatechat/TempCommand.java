package ru.energomera.zabbixbot.command.privatechat;

import com.google.common.collect.ImmutableSet;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.emoji.WeatherIconContainer;
import ru.energomera.zabbixbot.service.SendMessageService;

public class TempCommand implements Command {
    WeatherIconContainer weatherIconContainer;
    private final SendMessageService sendMessageService;

    public TempCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {

        String chatId = update.getMessage().getChatId().toString();
        weatherIconContainer = new WeatherIconContainer();

        ImmutableSet<String> temp = weatherIconContainer.temp();

        for(String elem : temp) {
            String s = weatherIconContainer.retrieveWeatherIcon(elem);

            sendMessageService.sendMessage(chatId, s);
        }


    }
}
