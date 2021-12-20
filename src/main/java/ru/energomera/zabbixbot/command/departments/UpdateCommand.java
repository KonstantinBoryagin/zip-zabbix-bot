package ru.energomera.zabbixbot.command.departments;

import lombok.extern.slf4j.Slf4j;
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

import static ru.energomera.zabbixbot.icon.Icon.*;

/**
 * Класс реализует {@link Command}
 * Отвечает за реакцию на кнопку "Дополнить" под инцидентом в любой из цеховых групп.
 * Отправляет подсказки {@link UpdateCommand#TIP_MESSAGE}, {@link UpdateCommand#WARNING_MESSAGE}.
 * Записывает в map {@link UpdateCommand#userChoose} выбор пользователя, id сообщений и комментарий
 */
@Slf4j
public class UpdateCommand implements Command {
    private final SendMessageService sendMessageService;
    public static Map<User, List<Object>> userChoose = new HashMap<>();

    private final String notification = "Следуйте подсказкам! " + ARROW_HEADING_DOWN.get();

    public static final String TIP_MESSAGE = INFORMATION_SOURCE.get() + " *Подсказка\\:*\n"
            + "_*[%s](tg://user?id=%d)*\\, введите информацию которую хотите добавить в выбранное сообщение и нажмите "
            + ARROW_FORWARD.get() + "_";

    public static final String WARNING_MESSAGE = FLAME.get() + "*Важно\\!*    _Если передумали вносить информацию \\- нажмите_  "
            + ARROW_RIGHT.get() + "  *\\/CANCEL*  " + ARROW_LEFT.get() + " \\!";


    public UpdateCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {

        String oldMessage = update.getCallbackQuery().getMessage().getText();
        String hashtag = findHashtag(oldMessage);

        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        User user = update.getCallbackQuery().getFrom();
        String callBackQueryId = update.getCallbackQuery().getId();

        //создает List для хранения id редактируемого сообщения, служебных сообщений, текущего сообщения и id чата
        List<Object> messagesIdForUser = new ArrayList<>();
        messagesIdForUser.add(oldMessage);
        messagesIdForUser.add(chatId);
        messagesIdForUser.add(messageId);

        //всплывающее уведомление в чате
        sendMessageService.sendAnswer(callBackQueryId, notification);

        Long userId = user.getId();
        String signature = user.getLastName() == null ? user.getFirstName() : user.getFirstName() + " " + user.getLastName();

        String tipMessage = String.format(TIP_MESSAGE, signature, userId);

        //ответ на сообщение от бота (отобразится только у данного пользователя в группе)
        ForceReplyKeyboard forceReplyKeyboard = ForceReplyKeyboard.builder()
                .inputFieldPlaceholder("Следуйте полученным подсказкам ... ")   //появится в поле ввода у пользователя
                .selective(true)
                .forceReply(true)
                .build();

        //отправляет сообщение-подсказку(с принудительным ответом на него) и сообщение-предупреждение
        Integer tipMessageId = sendMessageService.sendMessageWithReplyMarkDown2(chatId, tipMessage, forceReplyKeyboard);
        Integer warningMessageId = sendMessageService.sendMessageWithReplyMarkDown2(chatId, WARNING_MESSAGE);

        messagesIdForUser.add(tipMessageId);
        messagesIdForUser.add(warningMessageId);
        messagesIdForUser.add(hashtag);

        //добавляет List с информацией для текущего пользователя в map по ключу-(текущему)пользователю
        userChoose.put(user, messagesIdForUser);
        log.info("user {} press \"Edit message\" to {} message", signature, hashtag);
    }

    /**
     * Ищет hashtag в тексте сообщения
     * @param text текст сообщения об инциденте
     * @return hashtag
     */
    private String findHashtag(String text) {
        String regex = "#incident_(\\d+){5,}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        return matcher.find() ? matcher.group(0) : "";
    }
}
