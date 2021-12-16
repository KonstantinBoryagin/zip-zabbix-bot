package ru.energomera.zabbixbot.model.weather.current;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import ru.energomera.zabbixbot.model.weather.WeatherResult;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeatherResponse {
    @JsonProperty("weather")
    private WeatherResult[] weatherResult;

    @JsonProperty("main")
    private TemperatureResult temperatureResult;

    private Wind wind;

    @JsonProperty("dt")
    private long unixTime;

    @JsonProperty("sys")
    private SunnyTime sunnyTime;

    @JsonProperty("name")
    private String cityName;
}
