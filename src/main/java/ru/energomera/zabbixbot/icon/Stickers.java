package ru.energomera.zabbixbot.icon;


import lombok.Getter;

/**
 * Хранилище стикеров
 */
public enum Stickers {
    RABBIT_BU("CAACAgIAAxkBAAIBTWGjZm_RsPFshdyLBB_o4p2Losk-AAJDEAACabOQSw70C6DnoM32IgQ"),
    PIG("CAACAgIAAxkBAAIBLWGjWMttyBCbzBlLjC3A3f6mczqQAAKkVQACns4LAAGOZMQBA9B_syIE");

    @Getter
    private String stickerId;

    Stickers(String stickerId) {
        this.stickerId = stickerId;
    }

}
