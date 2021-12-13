//package ru.energomera.zabbixbot.command;
//
//import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
//import org.telegram.telegrambots.meta.api.methods.ParseMode;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.api.objects.User;
//import ru.energomera.zabbixbot.service.SendMessageService;
//
//import static ru.energomera.zabbixbot.sticker.Icon.FLAME;
//
//public class EditCommand implements Command{
//    private final SendMessageService sendMessageService;
//
//    public EditCommand(SendMessageService sendMessageService) {
//        this.sendMessageService = sendMessageService;
//    }
//
//    @Override
//    public void execute(Update update) {
//        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
//        String callBackQueryId = update.getCallbackQuery().getId();
//        User user = update.getCallbackQuery().getFrom();
//
//        AnswerCallbackQuery callbackQuery = AnswerCallbackQuery.builder()
//                .text("Следуйте подсказкам")
//                .callbackQueryId(callBackQueryId)
//                .build();
//
//        sendMessageService.sendTest(callbackQuery);
//
//        String signature = user.getLastName() == null ? user.getFirstName() : user.getFirstName() + " " + user.getLastName();
//
//        String text = FLAME.get() + "_*" + signature + "*, если хотите *добавить информацию* нажмите \\- _\\/button" + FLAME.get();
//
//        SendMessage message = SendMessage.builder()
//                .parseMode(ParseMode.MARKDOWNV2)
//                .chatId(chatId)
//                .text(text)
//                .build();
//
//        sendMessageService.sendTest(message);
//    }
//}
