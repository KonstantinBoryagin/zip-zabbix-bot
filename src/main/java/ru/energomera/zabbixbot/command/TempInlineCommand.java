//package ru.energomera.zabbixbot.command;
//
//import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.api.objects.User;
//import ru.energomera.zabbixbot.service.SendMessageService;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class TempInlineCommand implements Command {
//    private final SendMessageService sendMessageService;
//    public static Map<User, List<String>> userPrivateChoose = new HashMap<>();
//
//    public TempInlineCommand(SendMessageService sendMessageService) {
//        this.sendMessageService = sendMessageService;
//    }
//
//    @Override
//    public void execute(Update update) {
//        String id = update.getInlineQuery().getId();
//
////        for (int i = 0; i < splitQuery.length; i++) {
////            System.out.print("split -- " + i + ":   " + splitQuery[i] + " || ");
////        }
//
//
//        AnswerInlineQuery build3 = AnswerInlineQuery.builder().inlineQueryId(id).switchPmText("Edit message").switchPmParameter("123").build();
//        sendMessageService.sendTest(build3);
//    }
//}
