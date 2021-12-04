package ru.energomera.zabbixbot.command;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.service.SendMessageService;

public class UpdateCommand implements Command {
    private final SendMessageService sendMessageService;

    public UpdateCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String commandFromUser = update.getCallbackQuery().getData();
        String callBackQueryId = update.getCallbackQuery().getId();

        AnswerCallbackQuery build = AnswerCallbackQuery.builder()
                .callbackQueryId(callBackQueryId)
                .showAlert(true)
                .text("Введите ваше сообщение: ")
                .build();

        sendMessageService.sendTest(build);

        sendMessageService.sendMessageWithReply(chatId, "красивая подсказка ");


    }
}
