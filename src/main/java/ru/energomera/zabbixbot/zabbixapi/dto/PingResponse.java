package ru.energomera.zabbixbot.zabbixapi.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PingResponse {
    private String jsonrpc;
    private PingResult[] pingResult;
    private int id;
}
