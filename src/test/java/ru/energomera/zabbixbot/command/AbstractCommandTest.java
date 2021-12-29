package ru.energomera.zabbixbot.command;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.energomera.zabbixbot.bot.ZabbixTelegramBot;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.service.SendMessageServiceImpl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public abstract class AbstractCommandTest {

    protected ZabbixTelegramBot telegramBot = Mockito.mock(ZabbixTelegramBot.class);
    protected SendMessageService sendMessageService = new SendMessageServiceImpl(telegramBot);

    protected abstract String getCommandName();

    protected abstract String getCommandMessage();

    protected abstract Command getCommand();

    @Test
    public void shouldProperlyExecuteCommand() throws TelegramApiException {
        Long chatId = 123123123L;

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        when(message.getChatId()).thenReturn(chatId);
        when(message.getText()).thenReturn(getCommandName());
        Message ms = new Message();
        ms.setMessageId(10);

        update.setMessage(message);

        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId.toString())
                .text(getCommandMessage())
                .disableWebPagePreview(false)
                .parseMode(ParseMode.HTML)
                .build();

        when(telegramBot.execute(sendMessage)).thenReturn(ms);
        getCommand().execute(update);

        Mockito.verify(telegramBot, times(1)).execute(sendMessage);
    }
}
