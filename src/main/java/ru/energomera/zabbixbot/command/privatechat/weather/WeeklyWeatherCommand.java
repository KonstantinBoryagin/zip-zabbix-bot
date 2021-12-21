package ru.energomera.zabbixbot.command.privatechat.weather;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.service.WeatherServiceImpl;

/**
 * Класс реализует {@link Command}
 * Отправляет недельный прогноз погоды {@link WeatherServiceImpl}
 */
@Slf4j
public class WeeklyWeatherCommand implements Command {
    private final SendMessageService sendMessageService;
    private final WeatherServiceImpl weatherServiceImpl;

    public WeeklyWeatherCommand(SendMessageService sendMessageService, WeatherServiceImpl weatherServiceImpl) {
        this.sendMessageService = sendMessageService;
        this.weatherServiceImpl = weatherServiceImpl;
    }

    @Override
    public void execute(Update update) {

        String chatId = update.getMessage().getChatId().toString();

        String[] message = weatherServiceImpl.formatWeeklyWeatherMessage();

        log.info("Try to send weekly weather forecast to {}", chatId);

        for (int i = 0; i < message.length; i++) {
            sendMessageService.sendMessage(chatId, message[i]);
        }
    }
}
