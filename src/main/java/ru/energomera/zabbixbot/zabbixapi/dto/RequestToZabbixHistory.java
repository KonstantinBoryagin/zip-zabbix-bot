package ru.energomera.zabbixbot.zabbixapi.dto;

import lombok.Data;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * DTO for request to Zabbix
 */
@Data
@ToString
public class RequestToZabbixHistory {
    private String jsonrpc = "2.0";
    private String method = "history.get";
    private Map<String, Object> params;
    private String auth = "4d8c3ea6b03d1e0dd65217c3dd995001";
    private int id = 1;

    public RequestToZabbixHistory(int itemId, int resultLimit) {
        params = new LinkedHashMap<>();
        params.put("output", "extend");
        params.put("history", 0);  // потом вынеси его в параметры
        params.put("itemids", itemId);
        params.put("sortfield", "clock");
        params.put("sortorder", "DESC");
        params.put("limit", resultLimit);
    }
}
