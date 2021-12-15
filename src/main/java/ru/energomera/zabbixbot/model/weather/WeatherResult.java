package ru.energomera.zabbixbot.model.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResult {
    @JsonProperty("description")
    private String weatherDescription;

    @JsonProperty("icon")
    private String weatherIcon;
}
