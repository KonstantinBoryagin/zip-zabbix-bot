package ru.energomera.zabbixbot.zabbixapi.dto.ping;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PingResponse {
    private String jsonrpc;
    private PingResult[] result;
    private int id;
}
