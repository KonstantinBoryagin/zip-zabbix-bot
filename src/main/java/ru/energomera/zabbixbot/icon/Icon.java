package ru.energomera.zabbixbot.icon;

import com.vdurmont.emoji.EmojiParser;

/**
 * Хранилище эмодзи, преобразуются с помощью зависимости java-icon и метода get()
 */
public enum Icon {
    CHECK(":white_check_mark:"),
    NOT(":x:"),
    ROBOT_FACE(":robot_face:"),
    GAME_DICE(":game_die:"),
    SLOT_MACHINE(":slot_machine:"),
    DARTS(":dart:"),
    BOWLING(":bowling:"),
    SOCCER(":soccer:"),
    BASKETBALL(":basketball:"),
    QUESTION(":question:"),
    BACK_ARROW(":back:"),
    BAR_CHART(":bar_chart:"),
    CHART_TO_UP(":chart_with_upwards_trend:"),
    CHART_TO_DOWN(":chart_with_downwards_trend:"),
    EXCLAMATION(":exclamation:"),
    PUSHPIN(":pushpin:"),
    ROUND_PUSHPIN(":round_pushpin:"),
    MAG_RIGHT(":mag_right:"),
    INFORMATION_SOURCE(":information_source:"),
    ARROW_FORWARD(":arrow_forward:"),
    FLAME(":fire:"),
    ARROW_HEADING_DOWN(":arrow_heading_down:"),
    FULL_MOON_WITH_FACE(":full_moon_with_face:"),
    ARROW_RIGHT(":arrow_right:"),
    ARROW_LEFT(":arrow_left:"),
    MAILBOX_WITH_MAIL(":mailbox_with_mail:"),
    SUNGLASSES(":sunglasses:"),
    CLOCK_2(":clock2:"),
    BULB(":bulb:"),
    THOUGHT_BALLOON(":thought_balloon:"),
    FACE_WITH_MONOCLE(":face_with_monocle:"),
    NO_ENTRY(":no_entry_sign:"),
    MONKEY(":monkey:"),
    CITY_SUNRISE(":city_sunrise:"),
    CITY_SUNSET(":night_with_stars:"),
    COMET(":comet:"),
    SUNNY(":sunny:"),
    SMALL_CLOUD(":partly_sunny:"),
    SCATTERED_CLOUD(":white_sun_behind_cloud:"),
    FULL_MOON(":full_moon:"),
    CLOUD(":cloud:"),
    MIST(":fog:"),
    THERMOMETER(":thermometer:"),
    WIND(":wind_blowing_face:"),
    NEW_MOON(":new_moon_with_face:"),
    CALENDAR(":calendar:"),
    MEMO(":memo:"),
    CHART_IMG(":part_alternation_mark:");

    private String value;

    public String get() {
        return EmojiParser.parseToUnicode(value);
    }

    Icon(String value) {
        this.value = value;
    }
}
