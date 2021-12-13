package ru.energomera.zabbixbot.command;

import lombok.Getter;

import static ru.energomera.zabbixbot.sticker.Icon.*;

/**
 * Enum для имен команд {@link Command}
 */
@Getter
public enum CommandName {
    START("/start"),
    HELP("/help"),
    MENU("/menu"),
    ZIP("ЗИП"),
    CANCEL("/cancel"),
    EDIT_INCIDENT("/edit_incident"),
    UPDATE("/update"),
    UNKNOWN_USER("/unknown_user"),
    BACK(BACK_ARROW.get() + "  Назад"),
    PROXY_PING_COMMAND(CHART_TO_UP.get() + "  Proxy ICMP ping"),
    CPU_SRV_ERP_2(BAR_CHART.get() + "  srv-erp 2 CPU utilization"),
    INTERNET_PING(CHART_TO_UP.get() + "  Internet resources ping"),
    SLOT(SLOT_MACHINE.get() + "  Сыграем?"),
    MENU_CHARTS(CHART_IMG.get() + "  Графики"),
    PROBLEM(FLAME.get() + "  Актуальные проблемы"),
    COMMUTATOR_PING(CHART_TO_DOWN.get() + "  Серверная(оптика) ping"),
    ERP_DISK_F(MAG_RIGHT.get() + "  srv-erp 2 F: disk used space");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

}
