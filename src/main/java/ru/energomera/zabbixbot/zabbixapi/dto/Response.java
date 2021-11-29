package ru.energomera.zabbixbot.zabbixapi.dto;

import lombok.Data;
import lombok.ToString;
import ru.energomera.zabbixbot.zabbixapi.dto.history.HistoryResult;

@Data
@ToString
public class Response {
    private String jsonrpc;
    private HistoryResult[] result;
    private int id;
}
