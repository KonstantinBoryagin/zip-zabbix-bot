package ru.energomera.zabbixbot.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.energomera.zabbixbot.command.CommandContainer;
import ru.energomera.zabbixbot.service.SendMessageServiceImpl;

import static ru.energomera.zabbixbot.command.CommandName.*;

/**
 * Класс для инициализации бота унаследованный от {@link TelegramLongPollingBot}
 */
@Component
public class ZabbixTelegramBot extends TelegramLongPollingBot {

    public static final String COMMAND_PREFIX = "/";

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    @Value("${bot.adminGroupChatId}")
    private String adminGroupChatId;

    @Value("${bot.group25_chatId}")
    private String group25ChatId;

    @Value("${bot.group7_chatId}")
    private String group7ChatId;

    @Value("${bot.group5_chatId}")
    private String group5ChatId;

    private final CommandContainer commandContainer;

    public ZabbixTelegramBot() {
        this.commandContainer = new CommandContainer(new SendMessageServiceImpl(this));
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }


    /**
     * Смотрит из какой группы пришло сообщение и на основе этого принимает решение о том что с ним делать:
     *  1 - одна из 3-х групп рассылки инцидентов для цехов
     *  2 - группа администраторов
     *  3 - личные сообщения, но только для тех кто состоит в группе администраторов
     *
     * @param update присылает сервер телеграмма при обращении к боту
     */
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            String message = update.getMessage().getText();

            if (message.equals(ZIP.getCommandName())) {
                commandContainer.retrieveGeneralCommand(ZIP.getCommandName()).execute(update);
            } else if (chatId.equals(group5ChatId) || chatId.equals(group7ChatId) || chatId.equals(group25ChatId)) {

                if (message.startsWith(COMMAND_PREFIX)) {
                    String commandIdentifier = message.split("@")[0].toLowerCase();

                    commandContainer.retrieveDepartmentCommand(commandIdentifier).execute(update);
                } else {
                    commandContainer.retrieveDepartmentCommand(TEMP2.getCommandName()).execute(update);
                }

            } else if (chatId.equals(adminGroupChatId)) {
                String commandIdentifier = message.split("@")[0].toLowerCase();

                if (commandIdentifier.equals(HELP.getCommandName())) {
                    commandContainer.retrieveGeneralCommand(HELP.getCommandName()).execute(update);
                }

            } else {

                User fromUser = update.getMessage().getFrom();

                if (isUserInAdminGroup(fromUser)) {

                    if(commandContainer.isPrivateChatCommandMapContainsCommand(message)){
                        commandContainer.retrievePrivateChatCommand(message).execute(update);
                    }

                } else {
                    commandContainer.retrieveGeneralCommand(UNKNOWN_USER.getCommandName()).execute(update);
                }

            }

        } else if (update.hasCallbackQuery()) {
            String message = update.getCallbackQuery().getData();

            if (message.equals(UPDATE.getCommandName())) {
                commandContainer.retrieveDepartmentCommand(UPDATE.getCommandName()).execute(update);
            }

        } else {
            Sticker sticker = update.getMessage().getSticker();
            if (sticker != null) {
                String fileId = sticker.getFileId();
                System.out.println(fileId);
            }
        }
//
//
//        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            String message = update.getMessage().getText().trim();
//
//            /////////////////////// worked - check user in admin group
//            User fromUser = update.getMessage().getFrom();
//            GetChatMember getChatMember = GetChatMember.builder()
//                    .chatId(adminGroupChatId)
//                    .userId(fromUser.getId())
//                    .build();
//            ChatMember chatMember = null;
//
//            try {
//                chatMember = execute(getChatMember);
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//                //log
//            }
//
//            String status = chatMember != null ? chatMember.getStatus() : "nope";
//            if (status.equals("creator")
//                    || status.equals("administrator")
//                    || status.equals("member")) {
//                System.out.println(status);
//            } else {
//                System.out.println(status);
//            }
//            //////////////////////
//
//            if (message.startsWith(COMMAND_PREFIX)) {
//                String commandIdentifier = message.split("@")[0].toLowerCase();
//
//                commandContainer.retrieveCommand(commandIdentifier).execute(update);
//            } else if (message.equals(PROBLEM.getCommandName())) {
//                commandContainer.retrieveCommand(message).execute(update);
//            } else if (message.contains("Сыграем")) {
//                commandContainer.retrieveCommand(DICE.getCommandName()).execute(update);
//            } else if (message.contains("Графики")) {
//                commandContainer.retrieveCommand(MENU_CHARTS.getCommandName()).execute(update);
//            } else if (message.equals("ЗИП")) {
//                commandContainer.retrieveCommand("ZipCommand").execute(update);
//            } else if (message.equals(BACK.get() + "  Назад")) {    //вынеси это в константы классов
//                commandContainer.retrieveCommand(MENU.getCommandName()).execute(update);
//            } else if (message.equals(PROXY_PING_COMMAND.getCommandName())) {
//                commandContainer.retrieveCommand(PROXY_PING_COMMAND.getCommandName()).execute(update);
//            } else {
////                commandContainer.retrieveCommand(.getCommandName()).execute(update);
//                commandContainer.retrieveCommand(TEMP2.getCommandName()).execute(update);
//            }
//        } else if (update.hasCallbackQuery()) {
//
//            String message = update.getCallbackQuery().getData();
//
//            if (message.startsWith(COMMAND_PREFIX)) {
//                String commandIdentifier = message.split("\\|")[0].toLowerCase();
////                System.out.println(commandIdentifier);
////                System.out.println(message.split("\\|")[1].toLowerCase());
//
//                commandContainer.retrieveCallBack(commandIdentifier).execute(update);
//            }
//
//        } else if (update.hasChannelPost()) {
//            String s = update.getChannelPost().getChatId().toString();
//            System.out.println(s);
////            commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
//            commandContainer.retrieveCommand(CHART.getCommandName()).execute(update);
//
//        } else if (update.hasInlineQuery()) {
//
//            String inlineQuery = update.getInlineQuery().getQuery();
////            System.out.println(inlineQuery);
////            System.out.println("HERE " );///////////////////////////////////////////////////////////////////
////            if(inlineQuery.equals("/start")) {
//            commandContainer.retrieveCommand(TEMP_INLINE.getCommandName()).execute(update);
////            }
//
//        } else {
//
//            Sticker sticker = update.getMessage().getSticker();
//            if (sticker != null) {
//                String fileId = sticker.getFileId();
//                System.out.println(fileId);
//            }
//        }
    }

    /**
     * Проверяет, состоит ли пользователь в группе администраторов
     * @param user от кого пришло сообщение
     * @return булево значение
     */
    private boolean isUserInAdminGroup(User user) {
        GetChatMember getChatMember = GetChatMember.builder()
                .chatId(adminGroupChatId)
                .userId(user.getId())
                .build();
        ChatMember chatMember = null;

        try {
            chatMember = execute(getChatMember);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            //log
        }

        String status = chatMember != null ? chatMember.getStatus() : "nope";
        if (status.equals("creator")
                || status.equals("administrator")
                || status.equals("member")) {
            System.out.println(status);
            return true;
        } else {
            System.out.println(status);
            return false;
        }
    }
}


