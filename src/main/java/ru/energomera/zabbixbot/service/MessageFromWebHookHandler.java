package ru.energomera.zabbixbot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.energomera.zabbixbot.bot.ZabbixTelegramBot;
import ru.energomera.zabbixbot.command.ChartCommand;
import ru.energomera.zabbixbot.zabbixapi.dto.ZabbixWebHook;

@Service
public class MessageFromWebHookHandler {

    private SendMessageService sendMessageService;

    @Autowired
    public MessageFromWebHookHandler(SendMessageService sendMessageService, ZabbixTelegramBot telegramBot) {
        this.sendMessageService = sendMessageService;
    }

    public void processInputMessage(ZabbixWebHook webHookEntity) {
        String chatId = webHookEntity.getChat_id();
        String subject = webHookEntity.getSubj();
        String message = webHookEntity.getMessage();

//        String[] split = line.split(System.getProperty("line.separator"));

        if(subject.contains("Internet Proxy ЗИП")) {
            ReplyKeyboard proxyShowButton = ChartCommand.replyChartOptions2("proxy");//потом вынести в костанту или типо того
            sendMessageService.sendMessageFromWebHookWithCallBackButton(chatId, subject, message, proxyShowButton);
        } else {
            sendMessageService.sendMessageFromWebHook(chatId, subject, message);
        }
    }


}
