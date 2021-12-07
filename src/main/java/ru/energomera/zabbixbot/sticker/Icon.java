package ru.energomera.zabbixbot.sticker;

import com.vdurmont.emoji.EmojiParser;

public enum Icon {
    PLUS(":heavy_plus_sign:"),
    MINUS(":heavy_minus_sign:"),
    CHECK(":white_check_mark:"),
    NOT(":x:"),
    DOUBT(":zzz:"),
    ROBOT_FACE(":robot_face:"),
    FLAG(":checkered_flag:"),
    ONE(":one:"),
    TWO(":two:"),
    THREE(":three:"),
    FOUR(":four:"),
    FIVE(":five:"),
    SIX(":one:"),
    POOP(":poop:"),
    GAME_DICE(":game_die:"),
    SLOT_MACHINE(":slot_machine:"),
    BACK(":back:"),
    EXCLAMATION(":exclamation:"),
    PUSHPIN(":pushpin:"),
    ROUND_PUSHPIN(":round_pushpin:"),
    WHITE_CHECK_MARK(":white_check_mark:"),
    INFORMATION_SOURCE(":information_source:"),
    ARROW_FORWARD(":arrow_forward:"),
    FLAME(":fire:"),
    CHART_IMG(":part_alternation_mark:");

    private String value;

    public String get() {
        return EmojiParser.parseToUnicode(value);
    }

    Icon(String value) {
        this.value = value;
    }
}
