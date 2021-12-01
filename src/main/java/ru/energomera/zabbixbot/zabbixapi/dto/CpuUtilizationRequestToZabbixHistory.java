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
//public class CpuUtilizationRequestToZabbixHistory extends RequestToZabbixHistory {
//
//        public CpuUtilizationRequestToZabbixHistory(int id, int limit) {
//            super(3);
//            HashMap<String, Object> params = new LinkedHashMap<>();
//            params.put("output", "extend");
//            params.put("history", 0);
//            params.put("itemids", id);                  //params.put("itemids", 32943);
//            params.put("sortfield", "clock");
//            params.put("sortorder", "DESC");
//            params.put("limit", limit);
//
//            super.setParams(params);
//        }
//
//}

