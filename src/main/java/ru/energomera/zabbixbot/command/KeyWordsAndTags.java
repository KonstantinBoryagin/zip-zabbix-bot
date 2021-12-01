package ru.energomera.zabbixbot.command;

import lombok.Getter;

import static ru.energomera.zabbixbot.sticker.Icon.POOP;

@Getter
public enum KeyWordsAndTags {
    PROXY_PING("Internet Proxy ЗИП", "\n " + POOP.get() + " #proxy_ping")
    ;


    private final String keyWord;
    private final String tag;

    KeyWordsAndTags(String keyWord, String tag) {
        this.keyWord = keyWord;
        this.tag = tag;
    }
}
