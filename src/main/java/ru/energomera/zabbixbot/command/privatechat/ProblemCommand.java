package ru.energomera.zabbixbot.command.privatechat;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.service.MessageFromWebHookHandler;
import ru.energomera.zabbixbot.service.SendMessageService;

import java.util.List;
import java.util.Map;

import static ru.energomera.zabbixbot.icon.Icon.*;
import static ru.energomera.zabbixbot.service.MessageFromWebHookHandler.messagesRepository;

/**
 * Класс реализует {@link Command}
 * Ответ на команду "/problem" в личном сообщении, отображает все не закрытые проблемы
 * присланные Zabbix (исключение - одна проблема, отсекается в {@link MessageFromWebHookHandler})
 */
@Slf4j
public class ProblemCommand implements Command {
    private final SendMessageService sendMessageService;
    private final String problemStatus = "  <b>Проблема: ";
    private final String noProblemMessage = "<b><i>Удивительно, но все в порядке  </i></b>" + FULL_MOON_WITH_FACE.get();

    public ProblemCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        if(messagesRepository.isEmpty()){
            sendMessageService.sendMessage(chatId, noProblemMessage);
        } else {
            for (Map.Entry<String, List<List<Object>>> problem : messagesRepository.entrySet()) {

                String problemText = problem.getKey();
                String[] splitProblemText = problemText.split("\n", 2);

                List<List<Object>> problemValue = problem.getValue();

                for (List<Object> problemProperties : problemValue) {
                    long problemStartTime = (long) problemProperties.get(1);
                    long problemTime = System.currentTimeMillis() - problemStartTime;
                    String formatProblemTime = String.format("%02dh:%02dm:%02ds", problemTime / 1000 / 3600,
                            problemTime / 1000 / 60 % 60, problemTime / 1000 % 60);

                    String message = FLAME.get() + problemStatus + "<i>" + splitProblemText[0] + "</i></b>\n\n"
                            + splitProblemText[1] + "\n\n"
                            + CLOCK_2.get() + "<i>  " + formatProblemTime + "</i>";

                    sendMessageService.sendMessage(chatId, message);

                }
            }
            User user = update.getMessage().getFrom();
            String firstname = user.getFirstName();
            String lastname = user.getLastName();
            String signature = lastname == null ? firstname : firstname + " " + lastname;
            log.info("sent actual problems for {}", signature);
        }
    }
}
