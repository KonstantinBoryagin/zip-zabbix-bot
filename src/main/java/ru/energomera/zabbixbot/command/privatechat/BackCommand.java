package ru.energomera.zabbixbot.command.privatechat;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.energomera.zabbixbot.command.Command;
import ru.energomera.zabbixbot.service.SendMessageService;

import java.util.ArrayList;
import java.util.List;

import static ru.energomera.zabbixbot.command.TempInlineCommand.userPrivateChoose;
import static ru.energomera.zabbixbot.service.MessageFromWebHookHandler.addInlineKeyboardToGroupNotificationPost;
import static ru.energomera.zabbixbot.sticker.Icon.PUSHPIN;

public class BackCommand implements Command {
    private final SendMessageService sendMessageService;

    public BackCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        Integer subQuery = Integer.valueOf(update.getCallbackQuery().getData().split("\\|")[1]);
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        User user = update.getCallbackQuery().getFrom();

        switch(subQuery) {
            case 1:
                Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
                List<List<InlineKeyboardButton>> keyboardList = addInlineKeyboardToGroupNotificationPost();

                InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(keyboardList);

                sendMessageService.sendTest(EditMessageReplyMarkup.builder()
                        .replyMarkup(keyboard)
                        .messageId(messageId)
                        .chatId(chatId)
                        .build());

                userPrivateChoose.remove(user);

                break;
            case 2:
                List<List<InlineKeyboardButton>> keyboardList2 = new ArrayList<>();
                List<InlineKeyboardButton> row2 = new ArrayList<>();
                row2.add(InlineKeyboardButton.builder().text(PUSHPIN.get() + "Switch").switchInlineQuery("").build());
                keyboardList2.add(row2);
                InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup(keyboardList2);

                sendMessageService.sendTest(SendMessage.builder()
                        .chatId(chatId)
                        .text("Back to group")
                        .replyMarkup(keyboardMarkup)
                        .build());

                userPrivateChoose.remove(user);
        }


    }
}
