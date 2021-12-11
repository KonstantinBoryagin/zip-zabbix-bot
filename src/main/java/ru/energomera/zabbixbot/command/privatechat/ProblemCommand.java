package ru.energomera.zabbixbot.command.privatechat;

import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.service.SendMessageService;

import java.util.Set;

import static ru.energomera.zabbixbot.service.MessageFromWebHookHandler.messagesRepository;

public class ProblemCommand implements Command {
    private final SendMessageService sendMessageService;

    public ProblemCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        Set<String> problemMessages = messagesRepository.keySet();

        for (String problem : problemMessages) {
            sendMessageService.sendTest(SendMessage.builder()
                    .chatId(chatId)
                    .parseMode(ParseMode.HTML)
                    .text(problem)
                    .build());
        }

    }
}
