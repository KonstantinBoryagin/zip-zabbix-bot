package ru.energomera.zabbixbot.zabbixapi.dto.srvrp2;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CpuUtilizationResponse {
    private String jsonrpc;
    private CpuUtilizationResult[] result;
    private int id;
}
