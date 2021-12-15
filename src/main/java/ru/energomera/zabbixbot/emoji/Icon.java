package ru.energomera.zabbixbot.emoji;

import com.vdurmont.emoji.EmojiParser;

/**
 * Хранилище эмодзи, преобразуются с помощью зависимости java-icon и метода get()
 */
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
    WHITE_CHECK_MARK(":white_check_mark:"),
    MAG_RIGHT(":mag_right:"),
    INFORMATION_SOURCE(":information_source:"),
    ARROW_FORWARD(":arrow_forward:"),
    FLAME(":fire:"),
    LEFTWARDS_ARROW(":leftwards_arrow_with_hook:"),
    HOURGLASS_FLOWING_SAND(":hourglass_flowing_sand:"),
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
    CLOUD_WITH_RAIN(":cloud_with_rain:"),
    SMALL_RAIN(":white_sun_behind_cloud_with_rain:"),
    THUNDER_CLOUD(":thunder_cloud_and_rain:"),
    SNOW_CLOUD(":cloud_with_snow:"),
    MIST(":fog:"),
    THERMOMETER(":thermometer:"),
    WIND(":wind_blowing_face:"),
    NEW_MOON(":new_moon_with_face:"),
    CHART_IMG(":part_alternation_mark:");

    private String value;

    public String get() {
        return EmojiParser.parseToUnicode(value);
    }

    Icon(String value) {
        this.value = value;
    }
}
