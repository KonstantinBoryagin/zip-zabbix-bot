package ru.energomera.zabbixbot.model.zabbix;

import lombok.Data;
import lombok.ToString;

/**
 * DTO for response from Zabbix
 */
@Data
@ToString
public class ResponseFromZabbixHistory {
    private String jsonrpc;
    private HistoryResponseResult[] result;
    private int id;
}
