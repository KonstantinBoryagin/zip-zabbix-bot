package ru.energomera.zabbixbot.zabbixapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@ToString
public class User {
//        "jsonrpc": "2.0",
//            "method": "user.login",
//            "params": {
//        "user": "Admin",
//                "password": "zabbix"
//    },
//        "id": 1,
//            "auth": null

    private String jsonrpc = "2.0";
    private String method = "user.login";
    private Map<String, Object> params;
    private int id;
    private String auth = null;


    public void init(int id){
        Map<String, Object> UserParams = new LinkedHashMap<>();
        UserParams.put("user", "BoryaginKR");
        UserParams.put("password", "20r!@g1n2");

        this.setParams(UserParams);
        this.setId(id);
    }

}
