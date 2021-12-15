package ru.energomera.zabbixbot.command.privatechat;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.service.WeatherService;

public class CurrentWeatherCommand implements Command {
    private final SendMessageService sendMessageService;

    public CurrentWeatherCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {

        String chatId = update.getMessage().getChatId().toString();
        WeatherService weatherService = new WeatherService();
        String message = weatherService.formCurrentWeatherMessage();

        sendMessageService.sendMessage(chatId, message);
    }
}
