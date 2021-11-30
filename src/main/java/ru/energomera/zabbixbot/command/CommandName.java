package ru.energomera.zabbixbot.command;

import lombok.Getter;

@Getter
public enum CommandName {
    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    ZABBIX("/zabbix"),
    PING("/ping"),
    HISTORY("Proxy ICMP ping"),
    CPU_SRV_ERP_2("SRV-ERP_2 CPU Utilization"),
    CHART("/chart"),
    NO("");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

}
