//package ru.energomera.zabbixbot.command;
//
//import org.telegram.telegrambots.meta.api.objects.Update;
//import ru.energomera.zabbixbot.service.SendMessageService;
//
//public class Temp3Command implements Command{
//    private final SendMessageService sendMessageService;
//    public static Integer currentMessageId = 0;
//
//    public Temp3Command(SendMessageService sendMessageService) {
//        this.sendMessageService = sendMessageService;
//    }
//
//    @Override
//    public void execute(Update update) {
////        String chatId = update.getMessage().getChatId().toString();
//        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
//        int messageId = update.getCallbackQuery().getMessage().getMessageId();
//        System.out.println("temp3 mesId - " + messageId);
//
//
//        currentMessageId = sendMessageService.sendMessageWithReplyMarkDown2(chatId, "Тут будет красивая подсказка, потом");
//        System.out.println("temp 3 - cuurent messageId - " + currentMessageId);
//
//
//
//    }
//}
