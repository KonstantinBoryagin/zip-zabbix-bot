package ru.energomera.zabbixbot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendDice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.energomera.zabbixbot.bot.ZabbixTelegramBot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
    public void shouldProperlySendMessage() throws TelegramApiException {

        String chatId = "test_chat_id";
        String message = "test_message";

        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .parseMode(ParseMode.HTML)
                .text(message)
                .disableWebPagePreview(false)
                .build();

        Message ms = new Message();
        ms.setMessageId(10);

        when(this.telegramBot.execute(sendMessage)).thenReturn(ms);

        Integer integer = sendMessageService.sendMessage(chatId, message);

//        Mockito.verify(telegramBot).execute(sendMessage);
        assertEquals(10, integer);

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