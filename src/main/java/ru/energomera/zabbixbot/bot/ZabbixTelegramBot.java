package ru.energomera.zabbixbot.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.energomera.zabbixbot.command.CommandContainer;
import ru.energomera.zabbixbot.controller.ZabbixRestService;
import ru.energomera.zabbixbot.service.SendMessageServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static ru.energomera.zabbixbot.command.CommandName.*;

/**
 * Класс для инициализации бота унаследованный от {@link TelegramLongPollingBot}
 */
@Component
@Slf4j
public class ZabbixTelegramBot extends TelegramLongPollingBot {

    public static final String COMMAND_PREFIX = "/";

    private List<User> membersOfAdminGroup = new ArrayList<>();

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
        this.commandContainer = new CommandContainer(new SendMessageServiceImpl(this)
        , new ZabbixRestService(new RestTemplateBuilder()));
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
    }

    /**
     * Проверяет, состоит ли пользователь в группе администраторов
     * @param user от кого пришло сообщение
     * @return булево значение
     */
    private boolean isUserInAdminGroup(User user) {
        if (membersOfAdminGroup.contains(user)) {
            ///////////////
            for(User member : membersOfAdminGroup) {
                System.out.println(member.getFirstName() + " is a member of admins group");
            }
            ///////////////
            return true;
        } else {
            GetChatMember getChatMember = GetChatMember.builder()
                    .chatId(adminGroupChatId)
                    .userId(user.getId())
                    .build();
            ChatMember chatMember = null;

            try {
                chatMember = execute(getChatMember);
            } catch (TelegramApiException e) {
                log.error("Can't execute getChatMember for {}", user.getId(), e);
            }

            String status = chatMember != null ? chatMember.getStatus() : "nope";
            if (status.equals("creator")
                    || status.equals("administrator")
                    || status.equals("member")) {
                log.info("User {} is {} in admins group", user.getFirstName(), status);
                membersOfAdminGroup.add(user);
                return true;
            } else {
                log.warn("Access denied. User {} is {} in admins group", user.getFirstName(), status);
                return false;
            }
        }
    }
}


