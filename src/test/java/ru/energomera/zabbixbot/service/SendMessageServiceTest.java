package ru.energomera.zabbixbot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendDice;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.energomera.zabbixbot.bot.ZabbixTelegramBot;

@DisplayName("Unit-level testing for SendMessageService")
class SendMessageServiceTest {

    private SendMessageService sendMessageService;
    private ZabbixTelegramBot telegramBot;

    @BeforeEach
    public void init() {
        telegramBot = Mockito.mock(ZabbixTelegramBot.class);
        sendMessageService = new SendMessageServiceImpl(telegramBot);
    }

    @Test
    public void shouldProperlySendEmoji() throws TelegramApiException {

        String chatId = "test_chat_id";
        String emoji = ":)";

        SendDice emojiToSend = SendDice.builder()
                .chatId(chatId)
                .emoji(emoji)
                .build();

        sendMessageService.sendEmoji(chatId, emoji);

        Mockito.verify(telegramBot).execute(emojiToSend);
    }

}