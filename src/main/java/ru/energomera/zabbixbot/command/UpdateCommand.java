package ru.energomera.zabbixbot.command;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.energomera.zabbixbot.service.SendMessageService;

import java.util.*;

import static ru.energomera.zabbixbot.sticker.Icon.*;

public class UpdateCommand implements Command {
    private final SendMessageService sendMessageService;
    public static Map<User, List<Object>> userChoose = new HashMap<>();

    public UpdateCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {

        Integer subCommand = Integer.valueOf(update.getCallbackQuery().getData().split("\\|")[1]);

        List<Object> messagesIdForUser = new ArrayList<>();

        String oldMessage;
        if (subCommand == 1) {
            oldMessage = update.getCallbackQuery().getMessage().getText()
                    + "\n ------------------------------------------------------ \n"
                    + PUSHPIN.get() + "    "
            ;
            System.out.println(subCommand);
        } else if (subCommand == 2) {
            oldMessage = update.getCallbackQuery().getMessage().getText()
                    + "\n ------------------------------------------------------ \n"
                    + WHITE_CHECK_MARK.get() + "    "
            ;
            System.out.println(subCommand);
        } else {
            oldMessage = update.getCallbackQuery().getMessage().getText() +
                    "\n ------------------------------------------------------ \n"
            ;
        }
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        User user = update.getCallbackQuery().getFrom();
        String callBackQueryId = update.getCallbackQuery().getId();

        messagesIdForUser.add(oldMessage);
        messagesIdForUser.add(chatId);
        messagesIdForUser.add(messageId);

        AnswerCallbackQuery build = AnswerCallbackQuery.builder()
                .callbackQueryId(callBackQueryId)
//                .showAlert(true)
                .text("Следуйте подсказкам! " + SUNGLASSES.get())
                .build();

        sendMessageService.sendTest(build);
//+ INFORMATION_SOURCE.get()    _Tip: Введите информацию которую хотите добавить и нажмите отправить._

        Long userId = user.getId();

        String signature = user.getLastName() == null ? user.getFirstName() : user.getFirstName() + " " + user.getLastName();
        /////////////////////    TO STRING FORMAT
        String tipMessage = INFORMATION_SOURCE.get() + " *Подсказка\\:*\n"
                + "_*[" + signature + "](tg://user?id=" + userId + ")*\\, введите информацию которую хотите добавить в выбранное сообщение и нажмите "
                + ARROW_FORWARD.get() + "_";


        String warningMessage = FLAME.get() + "_*Важно\\!* Если передумали вносить информацию \\- нажмите кнопку "
                + BACK.get() + "*Отменить*" +
                " под этим сообщением\\!_";


        ForceReplyKeyboard forceReplyKeyboard = ForceReplyKeyboard.builder()
                .inputFieldPlaceholder("Let's rock!")//появится в поле ввода у пользователя
                .selective(true)  //нужно где то взять ид сообщения или юзера
                .forceReply(true)
                .build();


        /////////////////////////////
        List<InlineKeyboardButton> row = new ArrayList<>(Arrays.asList(InlineKeyboardButton.builder()
                .text(BACK.get() + "Отменить")
                .callbackData("/cancel")
                .build()));
        InlineKeyboardMarkup cancelButton = InlineKeyboardMarkup.builder()
                .keyboardRow(row)
                .build();

        Integer tempMessage = sendMessageService.sendMessageWithReplyMarkDown2(chatId, tipMessage, forceReplyKeyboard);
        Integer tempMessage2 = sendMessageService.sendMessageWithReplyMarkDown2(chatId, warningMessage, cancelButton);

        messagesIdForUser.add(tempMessage);

        userChoose.put(user, messagesIdForUser);

        for (User name : userChoose.keySet()) {
            System.out.println(name + "  ---  userChoose.keySet()");
        }
    }
}
