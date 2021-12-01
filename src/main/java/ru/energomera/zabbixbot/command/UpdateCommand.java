package ru.energomera.zabbixbot.command;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.service.SendMessageService;

import static ru.energomera.zabbixbot.command.CommandName.UPDATE;

public class UpdateCommand implements Command {
    private final SendMessageService sendMessageService;

    public UpdateCommand(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        String commandFromUser = update.getCallbackQuery().getData();

        if(commandFromUser.contains(UPDATE.getCommandName())){

            //сделать сплит массива

            if(commandFromUser.contains("proxy")) {

                EditMessageText editMessageText = new EditMessageText();
                editMessageText.setChatId(chatId);
                editMessageText.setMessageId(messageId);
                editMessageText.setText("изменил его по полной");

//                CommandContainer commandContainer = new CommandContainer(sendMessageService);
//                commandContainer.retrieveCommand(HISTORY.getCommandName()).execute(update);

                    sendMessageService.sendChangedMessageFromWebHook(editMessageText);





//                    commandContainer.retrieveCommand(commandFromUser).execute(update);
//                    commandContainer.retrieveCommand(HISTORY.getCommandName()).execute(update);
            }

        } else {
            //log
        }
    }
}
