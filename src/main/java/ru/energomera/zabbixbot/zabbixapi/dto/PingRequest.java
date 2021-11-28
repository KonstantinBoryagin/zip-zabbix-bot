package ru.energomera.zabbixbot.zabbixapi.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
public class PingRequest {

//    {
//        "jsonrpc": "2.0",
//            "method": "trend.get",
//            "params": {
//                "output": [
//                        "clock",
//                        "value_max"
//                          ],
//                "itemids": [
//                       "33484"
//                       ],
//                   "limit": "20"
//            },
//        "auth": "4d8c3ea6b03d1e0dd65217c3dd995001",
//            "id": 1
//    }

    private String jsonrpc = "2.0";
    private String method = "trend.get";
    private Map<String, Object> params;
    private String auth = "4d8c3ea6b03d1e0dd65217c3dd995001";
    private int id = 1;
    List<String> output;

    public PingRequest() {
        params = new LinkedHashMap<>();
        output = new ArrayList<>();
        output.add("clock");
        output.add("value_max");

        params.put("output", output);
        params.put("itemids", 33484);
        params.put("limit", 100);
    }
}
