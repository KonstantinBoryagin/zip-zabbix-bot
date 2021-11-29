package ru.energomera.zabbixbot.zabbixapi.dto.ping;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PingResult {
    private long clock;
    private double value_max;
}
