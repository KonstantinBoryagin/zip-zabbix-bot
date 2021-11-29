package ru.energomera.zabbixbot.zabbixapi.dto.history;

import lombok.*;
import ru.energomera.zabbixbot.zabbixapi.dto.Request;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class HistoryRequest extends Request {

    public HistoryRequest(int id, int limit) {
        super(2);
        HashMap<String, Object> params = new LinkedHashMap<>();
        params.put("output", "extend");
        params.put("history", 0);
        params.put("itemids", id);
        params.put("sortfield", "clock");
        params.put("sortorder", "DESC");
        params.put("limit", limit);

        super.setParams(params);
    }

}
