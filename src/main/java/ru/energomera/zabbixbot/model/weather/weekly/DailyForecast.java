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

    @JsonProperty("moon_phase")
    private double moonPhase;

    @JsonProperty("temp")
    private Map<String, Double> dailyTemperature;

    @JsonProperty("feels_like")
    private Map<String, Double> feelsLike;

    private int pressure;
    private int humidity;

    @JsonProperty("wind_speed")
    private double windSpeed;

    @JsonProperty("weather")
    private WeatherResult[] weatherResult;

    @JsonProperty("clouds")
    private int cloudiness;

    @JsonProperty("pop")
    private double probabilityOfPrecipitation;
}
