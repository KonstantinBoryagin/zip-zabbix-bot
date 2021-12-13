package ru.energomera.zabbixbot.command;

import lombok.Getter;

import static ru.energomera.zabbixbot.sticker.Icon.*;

@Getter
public enum CommandName {
    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    MENU("/menu"),
    PROXY_PING_COMMAND(TWO.get() + "  Proxy ICMP ping"),
    CPU_SRV_ERP_2(ONE.get() + "  srv-erp 2 CPU utilization"),
    INTERNET_PING(THREE.get() + "  Internet resources ping"),
    UPDATE("/update"),
    YANDEX("/yandex"),
    SLOT(SLOT_MACHINE.get() + "  Сыграем?"),
    MENU_CHARTS(CHART_IMG.get() + "  Графики"),
    TEMP2("/temp2"),
    TEMP3("/temp3"),
    TEMP_INLINE("/temp_inline"),
    CLOSE("/close"),
    CHANGE("/change"),
    BACK(BACK_ARROW.get() + "  Назад"),
    BUTTON("/button"),
    EDIT("/edit"),
    CANCEL("/cancel"),
    PROBLEM(FLAME.get() + "  Актуальные проблемы"),
    ZIP("ЗИП"),
    UNKNOWN_USER("/unknown_user"),
    COMMUTATOR_PING(FOUR.get() + "  Серверная(оптика) ping"),
    ERP_DISK_F(FIVE.get() + "  srv-erp 2 F: disk used space"),
    TEMP("/temp");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

}
