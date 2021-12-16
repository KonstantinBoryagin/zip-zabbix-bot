package ru.energomera.zabbixbot.command.privatechat.weather;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.service.WeatherService;

/**
 * Класс реализует {@link Command}
 * Отправляет недельный прогноз погоды {@link WeatherService}
 */
public class WeeklyWeatherCommand implements Command {
    private final SendMessageService sendMessageService;
    private final WeatherService weatherService;

    public WeeklyWeatherCommand(SendMessageService sendMessageService, WeatherService weatherService) {
        this.sendMessageService = sendMessageService;
        this.weatherService = weatherService;
    }

    @Override
    public void execute(Update update) {

        String chatId = update.getMessage().getChatId().toString();

        String[] message = weatherService.formatWeeklyWeatherMessage();

        for (int i = 0; i < message.length; i++) {
            sendMessageService.sendMessage(chatId, message[i]);
        }
    }
}
