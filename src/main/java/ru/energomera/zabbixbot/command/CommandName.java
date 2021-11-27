package ru.energomera.zabbixbot.command;

import lombok.Getter;

@Getter
public enum CommandName {
    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    ZABBIX("/zabbix"),
    NO("");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

}
