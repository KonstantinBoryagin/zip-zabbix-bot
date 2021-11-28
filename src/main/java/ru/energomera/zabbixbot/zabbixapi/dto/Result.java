package ru.energomera.zabbixbot.zabbixapi.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Result {
    private long clock;
    private double value_max;
}
