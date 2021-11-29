package ru.energomera.zabbixbot.zabbixapi.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class HistoryResponse {
    private String jsonrpc;
    private HistoryResult[] HistoryResult;
    private int id;
}
