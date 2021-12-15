package ru.energomera.zabbixbot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import ru.energomera.zabbixbot.controller.WeatherRestController;
import ru.energomera.zabbixbot.model.weather.CurrentWeatherResponse;
import ru.energomera.zabbixbot.emoji.WeatherIconContainer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static ru.energomera.zabbixbot.emoji.Icon.*;

@Service
@Slf4j
public class WeatherService {
    private final WeatherRestController weatherRestController;
    private final WeatherIconContainer weatherIconContainer;

    public WeatherService() {
        this.weatherRestController = new WeatherRestController(new RestTemplateBuilder());
        this.weatherIconContainer = new WeatherIconContainer();
    }

    public String formCurrentWeatherMessage() {
        CurrentWeatherResponse currentWeather = weatherRestController.createGetToWeatherApi();

        String cityName = currentWeather.getCityName();
        double windSpeed = currentWeather.getWind().getWindSpeed();
        String weatherDescription = currentWeather.getWeatherResult()[0].getWeatherDescription();
        String weatherIcon = weatherIconContainer.retrieveWeatherIcon(
                currentWeather.getWeatherResult()[0].getWeatherIcon());
        double currentTemperature = currentWeather.getTemperatureResult().getCurrentTemperature();
        double temperatureFeelsLike = currentWeather.getTemperatureResult().getFeelsLike();
        String currentTime = formatUnixTimeToHoursAndMinutes(currentWeather.getUnixTime());
        String sunriseTime = formatUnixTimeToHoursAndMinutes(currentWeather.getSunnyTime().getSunriseTime());
        String sunsetTime = formatUnixTimeToHoursAndMinutes(currentWeather.getSunnyTime().getSunsetTime());

        String message = String.format("<b>Погода на <i>%s</i> в <i>%sе</i> </b>\n\n" +
                        "%s   <i><b>%s\n" +
                        "%s   %.1f</b> \u2103, ощущается как <b>%.1f</b> \u2103\n" +
                        "%s   <b>%.2f</b> м/сек\n\n" +
                        "%s   %s  -->  %s   %s</i>",
                currentTime, cityName, weatherIcon, weatherDescription, THERMOMETER.get(), currentTemperature,
                temperatureFeelsLike, WIND.get(), windSpeed, CITY_SUNRISE.get(), sunriseTime, CITY_SUNSET.get(), sunsetTime);

        return message;
    }

    private String formatUnixTimeToHoursAndMinutes(long unixTime) {

        Date date = new Date(unixTime * 1000);
        DateFormat dateFormatter = new SimpleDateFormat("HH:mm");
        String hour = dateFormatter.format(date);

        return hour;
    }
}
