package ru.energomera.zabbixbot.zabbixapi.dto.srvrp2;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CpuUtilizationResult {
    private long itemid;
    private long clock;
    private double value;
    private long ns;
}
