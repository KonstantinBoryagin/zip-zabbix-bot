package ru.energomera.zabbixbot.icon;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import ru.energomera.zabbixbot.service.WeatherServiceImpl;

import static ru.energomera.zabbixbot.icon.Icon.*;


/**
 * Контейнер для хранения идентификаторов погоды для {@link WeatherServiceImpl}
 * так как некоторые эмодзи не обрабатываются библиотекой java-emoji, то
 * они были просто скопированы из telegram
 */
public class WeatherIconContainer {
    private final ImmutableMap<String, String> weatherIconMap;
    private final String unknownIcon;

    public WeatherIconContainer(){

        weatherIconMap = ImmutableMap.<String, String>builder()
                .put("01d", SUNNY.get())
                .put("01n", FULL_MOON.get())
                .put("02d", SMALL_CLOUD.get())
                .put("02n", SMALL_CLOUD.get())
                .put("03d", SCATTERED_CLOUD.get())
                .put("03n", SCATTERED_CLOUD.get())
                .put("04d", CLOUD.get())
                .put("04n", CLOUD.get())
                .put("09d", "\uD83C\uDF27")     //эти иконки библиотека не переводит в эмодзи
                .put("09n", "\uD83C\uDF27")
                .put("10d", "\uD83C\uDF26")
                .put("10n", "\uD83C\uDF26")
                .put("11d", "⛈")
                .put("11n", "⛈")
                .put("13d", "\uD83C\uDF28")
                .put("13n", "\uD83C\uDF28")
                .put("50d", MIST.get())
                .put("50n", MIST.get())
                .build();

        unknownIcon = COMET.get();
    }

    public ImmutableSet<String> temp(){
        ImmutableSet<String> strings = weatherIconMap.keySet();
        return strings;
    }

    public String retrieveWeatherIcon(String iconCode){
        return weatherIconMap.getOrDefault(iconCode, unknownIcon);
    }

}
