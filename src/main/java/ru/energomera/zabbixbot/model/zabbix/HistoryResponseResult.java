package ru.energomera.zabbixbot.model.zabbix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

/**
 * DTO for response from Zabbix
 */
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryResponseResult {
    private long clock;
    private double value;
}
