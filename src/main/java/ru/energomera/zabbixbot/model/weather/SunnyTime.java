package ru.energomera.zabbixbot.model.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class SunnyTime {
    @JsonProperty("sunrise")
    private long sunriseTime;

    @JsonProperty("sunset")
    private long sunsetTime;
}
