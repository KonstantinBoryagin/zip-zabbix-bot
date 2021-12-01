//package ru.energomera.zabbixbot.zabbixapi.dto;
//
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//
//@Getter
//@Setter
//@ToString
//public class ProxyIcmpRequestToZabbixHistory extends RequestToZabbixHistory {
//
//    public ProxyIcmpRequestToZabbixHistory(int id, int limit) {
//        super(2);
//        HashMap<String, Object> params = new LinkedHashMap<>();
//        params.put("output", "extend");
//        params.put("history", 0);
//        params.put("itemids", id);
//        params.put("sortfield", "clock");
//        params.put("sortorder", "DESC");
//        params.put("limit", limit);
//
//        super.setParams(params);
//    }
//
//}
