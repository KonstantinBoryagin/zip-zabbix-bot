package ru.energomera.zabbixbot.zabbixapi.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResponseFromZabbixHistory {
    private String jsonrpc;
    private HistoryResponseResult[] result;
    private int id;
}
