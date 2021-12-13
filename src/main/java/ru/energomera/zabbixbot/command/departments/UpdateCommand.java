package ru.energomera.zabbixbot.command.departments;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.service.SendMessageService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.energomera.zabbixbot.sticker.Icon.*;

/**
 * Класс отвечает за реакцию на кнопку "Дополнить" под инцидентом в любой из цеховых групп.
 * Отправляет подсказки.
 * Записывает в map выбор пользователя, id сообщений и комментарий
 */
public class UpdateCommand implements Command {
    private final SendMessageService sendMessageService;
    public static Map<User, List<Object>> userChoose = new HashMap<>();

    private final String notification = "Следуйте подсказкам! " + ARROW_HEADING_DOWN.get();

    public static final String TIP_MESSAGE = INFORMATION_SOURCE.get() + " *Подсказка\\:*\n"
            + "_*[%s](tg://user?id=%d)*\\, введите информацию которую хотите добавить в выбранное сообщение и нажмите "
            + ARROW_FORWARD.get() + "_";

    public static final String WARNING_MESSAGE = FLAME.get() + "*Важно\\!* _Если передумали вносить информацию \\- нажмите_  "
            + ARROW_RIGHT.get() + "  *\\/CANCEL*  " + ARROW_LEFT.get() + " \\!";


    public UpdateCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {

        List<Object> messagesIdForUser = new ArrayList<>();

        String oldMessage = update.getCallbackQuery().getMessage().getText();
        String hashtag = findHashtag(oldMessage);

        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        User user = update.getCallbackQuery().getFrom();
        String callBackQueryId = update.getCallbackQuery().getId();

        messagesIdForUser.add(oldMessage);
        messagesIdForUser.add(chatId);
        messagesIdForUser.add(messageId);

        sendMessageService.sendAnswer(callBackQueryId, notification);

        Long userId = user.getId();
        String signature = user.getLastName() == null ? user.getFirstName() : user.getFirstName() + " " + user.getLastName();

        String tipMessage = String.format(TIP_MESSAGE, signature, userId);

        ForceReplyKeyboard forceReplyKeyboard = ForceReplyKeyboard.builder()
                .inputFieldPlaceholder("Let's rock!")   //появится в поле ввода у пользователя
                .selective(true)
                .forceReply(true)
                .build();

        Integer tipMessageId = sendMessageService.sendMessageWithReplyMarkDown2(chatId, tipMessage, forceReplyKeyboard);
        Integer warningMessageId = sendMessageService.sendMessageWithReplyMarkDown2(chatId, WARNING_MESSAGE);

        messagesIdForUser.add(tipMessageId);
        messagesIdForUser.add(warningMessageId);
        messagesIdForUser.add(hashtag);

        userChoose.put(user, messagesIdForUser);

        /////////////////////////////////////////////////////////////////////////////////////////////
        for (User name : userChoose.keySet()) {
            System.out.println(name + "  ---  userChoose.keySet()");
        }
    }

    private String findHashtag(String text) {
        String regex = "#incident_(\\d+){5,}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        return matcher.find() ? matcher.group(0) : "";
    }
}
