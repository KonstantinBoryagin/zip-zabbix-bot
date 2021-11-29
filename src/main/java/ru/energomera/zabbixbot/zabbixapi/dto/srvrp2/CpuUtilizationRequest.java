package ru.energomera.zabbixbot.zabbixapi.dto.srvrp2;

import lombok.Data;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@ToString
public class CpuUtilizationRequest {

        private String jsonrpc = "2.0";
        private String method = "history.get";
        private Map<String, Object> params;
        private String auth = "4d8c3ea6b03d1e0dd65217c3dd995001";
        private int id = 3;

        public CpuUtilizationRequest() {

            params = new LinkedHashMap<>();
            params.put("output", "extend");
            params.put("history", 0);
            params.put("itemids", 32943);
            params.put("sortfield", "clock");
            params.put("sortorder", "DESC");
            params.put("limit", 20);
        }

}
