package ru.energomera.zabbixbot.zabbixapi.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@ToString
public class Request {
    private String jsonrpc = "2.0";
    private String method = "history.get";
    private Map<String, Object> params;
    private String auth = "4d8c3ea6b03d1e0dd65217c3dd995001";
    private int id;

    public Request(int id) {
        this.id = id;
    }
}
