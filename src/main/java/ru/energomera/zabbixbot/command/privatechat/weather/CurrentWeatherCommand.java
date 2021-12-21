package ru.energomera.zabbixbot.command.privatechat.weather;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.service.WeatherServiceImpl;

/**
 * Класс реализует {@link Command}
 * Отправляет прогноз погоды на данный момент {@link WeatherServiceImpl}
 */
@Slf4j
public class CurrentWeatherCommand implements Command {
    private final SendMessageService sendMessageService;
    private final WeatherServiceImpl weatherServiceImpl;

    public CurrentWeatherCommand(SendMessageService sendMessageService, WeatherServiceImpl weatherServiceImpl) {
        this.sendMessageService = sendMessageService;
        this.weatherServiceImpl = weatherServiceImpl;
    }

    @Override
    public void execute(Update update) {

        String chatId = update.getMessage().getChatId().toString();
        String message = weatherServiceImpl.formCurrentWeatherMessage();

        log.info("Try to send current moment weather forecast to {}", chatId);
        sendMessageService.sendMessage(chatId, message);
    }
}
