//package ru.energomera.zabbixbot.command;
//
//import org.telegram.telegrambots.meta.api.methods.ParseMode;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
//import ru.energomera.zabbixbot.service.SendMessageService;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static ru.energomera.zabbixbot.sticker.Icon.FLAME;
//import static ru.energomera.zabbixbot.sticker.Icon.ROUND_PUSHPIN;
//
//public class ButtonCommand implements Command {
//    private final SendMessageService sendMessageService;
//
//    public ButtonCommand(SendMessageService sendMessageService) {
//        this.sendMessageService = sendMessageService;
//    }
//
//    @Override
//    public void execute(Update update) {
//        String chatId = update.getMessage().getChatId().toString();
//        Integer messageId = update.getMessage().getMessageId();
//
////// СДЕЛАЙ ЕЕ ВЫЗОВ С ПРОВЕРКОЙ ЕСТЬ ЛИ ID ПОЛЬЗОВАТЕЛЯ В МАПЕ /////////////////////
//
//        List<KeyboardButton> button = Arrays.asList(KeyboardButton.builder()
//                .text(FLAME.get() + "Редактировать")
//                .build());
//        KeyboardRow keyboardRow = new KeyboardRow(button);
//
//        ReplyKeyboardMarkup buttonMarkup = ReplyKeyboardMarkup.builder()
//                .resizeKeyboard(true)
//                .oneTimeKeyboard(true)
//                .selective(true)
//                .keyboardRow(keyboardRow)
//                .build();
//
//        String text = ROUND_PUSHPIN.get() + "_Here will be instructions_" + ROUND_PUSHPIN.get();
//
//        SendMessage message = SendMessage.builder()
//                .chatId(chatId)
//                .replyMarkup(buttonMarkup)
//                .replyToMessageId(messageId)
//                .text(text)
//                .parseMode(ParseMode.MARKDOWNV2)
//                .disableNotification(true)
//                .build();
//
//        sendMessageService.sendTest(message);
//
//
//        ///////////////////////////////
////        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
////        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
////        User user = update.getCallbackQuery().getFrom();
////        String callBackQueryId = update.getCallbackQuery().getId();
////
////        messagesIdForUser.add(oldMessage);
////        messagesIdForUser.add(chatId);
////        messagesIdForUser.add(messageId);
////
////        AnswerCallbackQuery build = AnswerCallbackQuery.builder()
////                .callbackQueryId(callBackQueryId)
//////                .showAlert(true)
////                .text("Введите ваше сообщение: ")
////                .build();
////
////        sendMessageService.sendTest(build);
//////+ INFORMATION_SOURCE.get()    _Tip: Введите информацию которую хотите добавить и нажмите отправить._
////        String tipMessage = "*Редактировать\\:*\n" + ROUND_PUSHPIN.get()
////                + "_Tip: Введите информацию которую хотите добавить и нажмите _"
////                + ARROW_FORWARD.get();
////        Integer tempMessage = sendMessageService.sendMessageWithReplyMarkDown2(chatId, tipMessage);
////        messagesIdForUser.add(tempMessage);
////
////        userChoose.put(user, messagesIdForUser);
////
////        for(User name: userChoose.keySet()){
////            System.out.println(name + "  ---  userChoose.keySet()");
////        }
//    }
//}
