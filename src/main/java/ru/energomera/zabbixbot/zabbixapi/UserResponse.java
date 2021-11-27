package ru.energomera.zabbixbot.zabbixapi;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserResponse {
    private String jsonrpc;
    private String result;
    private int id;
}
