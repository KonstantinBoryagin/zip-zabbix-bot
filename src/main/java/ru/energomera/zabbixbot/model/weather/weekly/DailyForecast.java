package ru.energomera.zabbixbot.model.weather.weekly;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import ru.energomera.zabbixbot.model.weather.WeatherResult;

import java.util.Map;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyForecast {
    @JsonProperty("dt")
    private long unixTime;

    @JsonProperty("sunrise")
    private long sunriseTime;

    @JsonProperty("sunset")
    private long sunsetTime;

    @JsonProperty("temp")
    private Map<String, Double> dailyTemperature;

    @JsonProperty("feels_like")
    private Map<String, Double> feelsLike;

    @JsonProperty("wind_speed")
    private double windSpeed;

    @JsonProperty("weather")
    private WeatherResult[] weatherResult;

    @JsonProperty("pop")
    private double probabilityOfPrecipitation;
}
