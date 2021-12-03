package ru.energomera.zabbixbot.command;

import lombok.Getter;

@Getter
public enum CommandName {
    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    MENU("/menu"),
    PROXY_PING_COMMAND("Proxy ICMP ping"),
    CPU_SRV_ERP_2("SRV-ERP_2 CPU Utilization"),
    CHART("/chart"),
    UPDATE("/update "),
    YANDEX("/yandex"),
    DICE("/dice"),
    MENU_CHARTS("/menu_charts"),
    TEMP("/temp");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

}
