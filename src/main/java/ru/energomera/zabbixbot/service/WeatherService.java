package ru.energomera.zabbixbot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import ru.energomera.zabbixbot.controller.WeatherRestController;
import ru.energomera.zabbixbot.model.weather.current.CurrentWeatherResponse;
import ru.energomera.zabbixbot.emoji.WeatherIconContainer;
import ru.energomera.zabbixbot.model.weather.weekly.DailyForecast;
import ru.energomera.zabbixbot.model.weather.weekly.WeeklyWeatherResponse;

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
        CurrentWeatherResponse currentWeather = weatherRestController.createGetForCurrentWeatherToWeatherApi();

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

    public String formatWeeklyWeatherMessage() {
        WeeklyWeatherResponse weeklyWeather = weatherRestController.createGetForWeeklyWeatherToWeatherApi();
        DailyForecast[] dailyForecast = weeklyWeather.getDailyForecast();
        StringBuilder message = new StringBuilder();

        for (int i = 1; i < dailyForecast.length; i++) {
            String day = formatUnixTimeToDays(dailyForecast[i].getUnixTime());
            String sunriseTime = formatUnixTimeToHoursAndMinutes(dailyForecast[i].getSunriseTime());
            String sunsetTime = formatUnixTimeToHoursAndMinutes(dailyForecast[i].getSunsetTime());
            double dayTemperature = dailyForecast[i].getDailyTemperature().getOrDefault("day", 0.0);
            double dayMinTemperature = dailyForecast[i].getDailyTemperature().getOrDefault("min", 0.0);
            double dayMaxTemperature = dailyForecast[i].getDailyTemperature().getOrDefault("max", 0.0);
            double nightTemperature = dailyForecast[i].getDailyTemperature().getOrDefault("night", 0.0);
            double feelsLikeDay = dailyForecast[i].getFeelsLike().getOrDefault("day", 0.0);
            double feelsLikeNight = dailyForecast[i].getFeelsLike().getOrDefault("night", 0.0);
            double windSpeed = dailyForecast[i].getWindSpeed();
            int probabilityOfPrecipitation = (int) (dailyForecast[i].getProbabilityOfPrecipitation() * 100);
            String weatherDescription = dailyForecast[i].getWeatherResult()[0].getWeatherDescription();
            String weatherIcon = weatherIconContainer.retrieveWeatherIcon(
                    dailyForecast[i].getWeatherResult()[0].getWeatherIcon());

            String dailyMessage = String.format("%s  -->  %s  %s\n\n" +
                            "%s   <i>вероятность осадков:</i> %d%%" +
                            "%s   днем: %.1f \u2103, feels like: %.1f \u2103 \n" +
                            "%s   ночью: %.1f \u2103, feels like: %.1f \u2103 \n" +
                            "%s   min: %.1f \u2103, max: %.1f \u2103 \n" +
                            "%s   <b>%.2f</b> <i>м/сек</i>\n" +
                            "%s   %s  -->  %s   %s \n" +
                            "-----------------------------------------------------\n",
                    day, weatherIcon, weatherDescription,
                    UMBRELLA.get(), probabilityOfPrecipitation,
                    THERMOMETER.get(), dayTemperature, feelsLikeDay,
                    THERMOMETER.get(), nightTemperature, feelsLikeNight,
                    THERMOMETER.get(), dayMinTemperature, dayMaxTemperature,
                    WIND.get(), windSpeed,
                    CITY_SUNRISE.get(), sunriseTime, CITY_SUNSET.get(), sunsetTime);

                    message.append(dailyMessage);
        }

        return message.toString();

    }

    private String formatUnixTimeToHoursAndMinutes(long unixTime) {

        Date date = new Date(unixTime * 1000);
        DateFormat dateFormatter = new SimpleDateFormat("HH:mm");
        String hour = dateFormatter.format(date);

        return hour;
    }

    private String formatUnixTimeToDays(long unixTime) {

        Date date = new Date(unixTime * 1000);
        DateFormat dateFormatter = new SimpleDateFormat("E d.M");
        String day = dateFormatter.format(date);

        return day;
    }
}
