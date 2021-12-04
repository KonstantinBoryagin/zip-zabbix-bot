package ru.energomera.zabbixbot.command;

import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import ru.energomera.zabbixbot.service.SendMessageService;

public class TempInlineCommand implements Command{
    private final SendMessageService sendMessageService;
    public static Integer currentMessageId = 0;

    public TempInlineCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String id = update.getInlineQuery().getId();

        AnswerInlineQuery build = AnswerInlineQuery.builder().inlineQueryId(id).switchPmText("Edit message").switchPmParameter("123").build();
        InputTextMessageContent hello_hello = InputTextMessageContent.builder().messageText("Hello hello").build();

        System.out.println(update.getInlineQuery().getQuery());
        Long userId = update.getInlineQuery().getFrom().getId();
        System.out.println(userId);
//        sendMessageService.sendTest(hello_hello);
        sendMessageService.sendTest(build);
    }
}
