package ru.energomera.zabbixbot.command;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.energomera.zabbixbot.service.SendMessageService;

import java.util.ArrayList;
import java.util.List;

import static ru.energomera.zabbixbot.command.TempInlineCommand.userPrivateChoose;
import static ru.energomera.zabbixbot.service.MessageFromWebHookHandler.inlineChangeButton;

public class ChangeCommand implements Command{
    private final SendMessageService sendMessageService;

    public ChangeCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        Integer subQuery = Integer.valueOf(update.getCallbackQuery().getData().split("\\|")[1]);
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();

        if(subQuery == 1) {

            Integer messageId = update.getCallbackQuery().getMessage().getMessageId();

            List<List<InlineKeyboardButton>> keyboardList = new ArrayList<>();

            List<InlineKeyboardButton> row1 = inlineChangeButton(update);
            List<InlineKeyboardButton> row2 = new ArrayList<>();
            row2.add(InlineKeyboardButton.builder().text("Back").callbackData("/back|1").build());
            keyboardList.add(row1);
            keyboardList.add(row2);


            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(keyboardList);


            sendMessageService.sendTest(EditMessageReplyMarkup.builder()
                    .replyMarkup(keyboard)
                    .messageId(messageId)
                    .chatId(chatId)
                    .build());

        } else if(subQuery == 2) {

            String callBackQueryId = update.getCallbackQuery().getId();

            User user = update.getCallbackQuery().getFrom();
            if(userPrivateChoose.containsKey(user)) {
                AnswerCallbackQuery build = AnswerCallbackQuery.builder()
                        .callbackQueryId(callBackQueryId)
                        .text("Введите ваше сообщение: ")
                        .build();

                sendMessageService.sendTest(build);

                sendMessageService.sendMessageWithReplyMarkDown2(chatId, "красивая подсказка ");
            }
        }
    }
}
