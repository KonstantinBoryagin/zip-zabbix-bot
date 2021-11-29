package ru.energomera.zabbixbot.command;

import lombok.Getter;

@Getter
public enum CommandName {
    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    ZABBIX("/zabbix"),
    PING("/ping"),
    HISTORY("/history"),
    CPU_SRV_ERP_2("/cpu_srv_erp_2"),
    NO("");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

}
