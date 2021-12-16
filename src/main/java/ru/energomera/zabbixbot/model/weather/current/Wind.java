package ru.energomera.zabbixbot.model.weather.current;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Wind {
    @JsonProperty("speed")
    private double windSpeed;
}
