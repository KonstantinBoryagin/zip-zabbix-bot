package ru.energomera.zabbixbot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import ru.energomera.zabbixbot.controller.WeatherRestController;
import ru.energomera.zabbixbot.icon.WeatherIconContainer;
import ru.energomera.zabbixbot.model.weather.current.CurrentWeatherResponse;
import ru.energomera.zabbixbot.model.weather.weekly.DailyForecast;
import ru.energomera.zabbixbot.model.weather.weekly.WeeklyWeatherResponse;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static ru.energomera.zabbixbot.icon.Icon.*;

/**
 * Класс для формирования сообщения о погоде на основе данных полученных от {@link WeatherRestController}
 */
@Service
@Slf4j
public class WeatherServiceImpl implements WeatherService {
    private final WeatherRestController weatherRestController;
    private final WeatherIconContainer weatherIconContainer;

    public WeatherServiceImpl() {
        this.weatherRestController = new WeatherRestController(new RestTemplateBuilder());
        this.weatherIconContainer = new WeatherIconContainer();
    }

    /**
     * Формирует прогноз погоды в данный момент
     * @return сообщение
     */
    @Override
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
                        "%s   %s  --&gt;  %s   %s</i>",
                currentTime, cityName, weatherIcon, weatherDescription, THERMOMETER.get(), currentTemperature,
                temperatureFeelsLike, WIND.get(), windSpeed, CITY_SUNRISE.get(), sunriseTime, CITY_SUNSET.get(), sunsetTime);

        log.info("Formed current moment weather forecast");
        return message;
    }

    /**
     * Формирует прогноз погоды на неделю
     * @return массив сообщений
     */
    @Override
    public String[] formatWeeklyWeatherMessage() {
        WeeklyWeatherResponse weeklyWeather = weatherRestController.createGetForWeeklyWeatherToWeatherApi();
        DailyForecast[] dailyForecast = weeklyWeather.getDailyForecast();
        StringBuilder message1 = new StringBuilder();
        StringBuilder message2 = new StringBuilder();

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

            String dailyMessage = String.format("<b><i>%s:</i></b> \n\n" +
                            "%s   <i><b>%s</b></i>\n" +
                            "☂   <i>вероятность осадков: <b>%d</b></i>%%\n" +
                            "%s   <i>min: <b>%.1f</b> </i>\u2103, <i>max: <b>%.1f</b> </i>\u2103\n" +
                            "▫   <i>днем: <b>%.1f</b> </i>\u2103, <i>ощущается: <b>%.1f</b></i> \u2103 \n" +
                            "▪   <i>ночью: <b>%.1f</b> </i>\u2103, <i>ощущается: <b>%.1f</b></i> \u2103\n" +
                            "%s   <i><b>%.2f</b> м/сек</i>\n" +
                            "%s   <i>%s  --&gt;  %s   %s</i> \n" +
                            "-------------------------------------------------------------\n",
                    day,
                    weatherIcon, weatherDescription,
                    probabilityOfPrecipitation,
                    THERMOMETER.get(), dayMinTemperature, dayMaxTemperature,
                    dayTemperature, feelsLikeDay,
                    nightTemperature, feelsLikeNight,
                    WIND.get(), windSpeed,
                    CITY_SUNRISE.get(), sunriseTime, CITY_SUNSET.get(), sunsetTime);

            if (i <= 4) {
                message1.append(dailyMessage);
            } else {
                message2.append(dailyMessage);
            }
        }

        //делим на 2 сообщения, так как у телеги начинаются проблемы с HTML форматированием у длинного сообщения
        String[] messages = {message1.toString(), message2.toString()};
        log.info("Formed weekly weather forecast");
        return messages;

    }

    /**
     * Формирует подробный прогноз погоды на сегодня и завтра
     * @return сообщение
     */
    @Override
    public String formatDailyWeatherMessage() {
        WeeklyWeatherResponse weeklyWeather = weatherRestController.createGetForWeeklyWeatherToWeatherApi();
        DailyForecast[] dailyForecast = weeklyWeather.getDailyForecast();
        StringBuilder message = new StringBuilder();

        for (int i = 0; i < 2; i++) {
            String day = formatUnixTimeToDays(dailyForecast[i].getUnixTime());
            String sunriseTime = formatUnixTimeToHoursAndMinutes(dailyForecast[i].getSunriseTime());
            String sunsetTime = formatUnixTimeToHoursAndMinutes(dailyForecast[i].getSunsetTime());
            double dayTemperature = dailyForecast[i].getDailyTemperature().getOrDefault("day", 0.0);
            double dayMinTemperature = dailyForecast[i].getDailyTemperature().getOrDefault("min", 0.0);
            double dayMaxTemperature = dailyForecast[i].getDailyTemperature().getOrDefault("max", 0.0);
            double nightTemperature = dailyForecast[i].getDailyTemperature().getOrDefault("night", 0.0);
            double eveningTemperature = dailyForecast[i].getDailyTemperature().getOrDefault("eve", 0.0);
            double morningTemperature = dailyForecast[i].getDailyTemperature().getOrDefault("morn", 0.0);
            double feelsLikeDay = dailyForecast[i].getFeelsLike().getOrDefault("day", 0.0);
//            double feelsLikeNight = dailyForecast[i].getFeelsLike().getOrDefault("night", 0.0);
//            double feelsLikeEvening = dailyForecast[i].getFeelsLike().getOrDefault("eve", 0.0);
//            double feelsLikeMorning = dailyForecast[i].getFeelsLike().getOrDefault("morn", 0.0);
            double windSpeed = dailyForecast[i].getWindSpeed();
            int pressure = dailyForecast[i].getPressure();
            int humidity = dailyForecast[i].getHumidity();
            int cloudiness = dailyForecast[i].getCloudiness();
            String moonPhaseEmoji = moonPhaseToEmoji(dailyForecast[i].getMoonPhase());
            int probabilityOfPrecipitation = (int) (dailyForecast[i].getProbabilityOfPrecipitation() * 100);
            String weatherDescription = dailyForecast[i].getWeatherResult()[0].getWeatherDescription();
            String weatherIcon = weatherIconContainer.retrieveWeatherIcon(
                    dailyForecast[i].getWeatherResult()[0].getWeatherIcon());

            String dailyMessage = String.format("<b><i>%s:</i></b> \n\n" +
                            "%s   <i><b>%s</b></i>\n" +
                            "☂   <i>вероятность осадков: <b>%d</b></i>%%\n\n" +
                            "%s   <i>min: <b>%.1f</b> </i>\u2103, <i>max: <b>%.1f</b> </i>\u2103\n" +
                            "▫   <i>утром: <b>%.1f</b> </i>\u2103\n" +
                            "▫   <i>днем: <b>%.1f</b> </i>\u2103, <i>ощущается: <b>%.1f</b></i> \u2103 \n" +
                            "▪   <i>вечером: <b>%.1f</b> </i>\u2103\n" +
                            "▪   <i>ночью: <b>%.1f</b> </i>\u2103\n\n" +
                            "%s   <i>ветер: <b>%.2f</b> м/сек</i>\n" +
                            "\uD83D\uDDDC   <i>давление: <b>%d</b> мм рт. ст.</i>\n" +
                            "\uD83D\uDCA7   <i>влажность: <b>%s</b>%%</i>\n\n" +
                            "\uD83D\uDCA8   <i>облачность: <b>%d</b>%%</i>\n" +
                            "%s   <i>&lt;-- фаза луны</i>\n" +
                            "%s   <i>%s  --&gt;  %s   %s</i> \n" +
                            "-------------------------------------------------------------\n",
                    day,
                    weatherIcon, weatherDescription,
                    probabilityOfPrecipitation,
                    THERMOMETER.get(), dayMinTemperature, dayMaxTemperature,
                    morningTemperature,
                    dayTemperature, feelsLikeDay,
                    eveningTemperature,
                    nightTemperature,
                    WIND.get(), windSpeed,
                    pressure,
                    humidity,
                    cloudiness,
                    moonPhaseEmoji,
                    CITY_SUNRISE.get(), sunriseTime, CITY_SUNSET.get(), sunsetTime);

                message.append(dailyMessage);
            }

        log.info("Formed daily weather forecast");
        return message.toString();
    }

    /**
     * Преобразует юникс время в формат часы:минуты
     * @param unixTime юникс время в секундах
     * @return время(строкой)
     */
    private String formatUnixTimeToHoursAndMinutes(long unixTime) {

        String formatTime = Instant.ofEpochSecond(unixTime)
                .atZone(ZoneId.of("Europe/Moscow"))
                .format(DateTimeFormatter.ofPattern("HH:mm"));

        return formatTime;
    }

    /**
     * Преобразует юникс время в формат "день_недели число.месяц"
     * @param unixTime юникс время в секундах
     * @return время(строкой)
     */
    private String formatUnixTimeToDays(long unixTime) {

        String formatDay = Instant.ofEpochSecond(unixTime)
                .atZone(ZoneId.of("Europe/Moscow"))
                .format(DateTimeFormatter.ofPattern("EEEE d.M", new Locale("ru")));

        return formatDay.substring(0, 1).toUpperCase(Locale.ROOT) + formatDay.substring(1);
    }

    /**
     * Возвращает эмодзи фазы луны
     * @param moonPhaseNumber фаза луны (от 0 до 1)
     * @return эмодзи фазы луны
     */
    private String moonPhaseToEmoji(double moonPhaseNumber) {
        String moonEmoji;
        if(moonPhaseNumber == 0 || moonPhaseNumber == 1) {
            moonEmoji = "\uD83C\uDF11";
        } else if(moonPhaseNumber > 0 && moonPhaseNumber < 0.25) {
            moonEmoji = "\uD83C\uDF12";
        }else if(moonPhaseNumber == 0.25) {
            moonEmoji = "\uD83C\uDF13";
        }else if(moonPhaseNumber > 0.25 && moonPhaseNumber < 0.5) {
            moonEmoji = "\uD83C\uDF14";
        } else if(moonPhaseNumber == 0.5) {
            moonEmoji = "\uD83C\uDF15";
        }else if(moonPhaseNumber > 0.5 && moonPhaseNumber < 0.75) {
            moonEmoji = "\uD83C\uDF16";
        } else if(moonPhaseNumber == 0.75) {
            moonEmoji = "\uD83C\uDF17";
        }else if(moonPhaseNumber > 0.75 && moonPhaseNumber < 1) {
            moonEmoji = "\uD83C\uDF18";
        } else {
            moonEmoji = "\uD83C\uDF1A"; //в случае некорректного входящего параметра
        }

        return moonEmoji;
    }
}
