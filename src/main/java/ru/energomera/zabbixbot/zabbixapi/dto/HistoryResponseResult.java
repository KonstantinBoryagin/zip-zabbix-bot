package ru.energomera.zabbixbot.zabbixapi.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class HistoryResponseResult {
    private long itemid;
    private long clock;
    private double value;
    private long ns;
}