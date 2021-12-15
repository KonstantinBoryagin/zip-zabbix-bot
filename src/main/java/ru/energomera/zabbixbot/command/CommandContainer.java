package ru.energomera.zabbixbot.command;

import com.google.common.collect.ImmutableMap;
import ru.energomera.zabbixbot.command.adminsgroup.HelpAdminsGroupCommand;
import ru.energomera.zabbixbot.command.departments.CancelCommand;
import ru.energomera.zabbixbot.command.departments.EditIncidentMessageCommand;
import ru.energomera.zabbixbot.command.departments.HelpCommand;
import ru.energomera.zabbixbot.command.departments.UpdateCommand;
import ru.energomera.zabbixbot.command.privatechat.*;
import ru.energomera.zabbixbot.command.privatechat.emoji.*;
import ru.energomera.zabbixbot.controller.ZabbixRestController;
import ru.energomera.zabbixbot.service.SendMessageService;

import static ru.energomera.zabbixbot.command.CommandName.*;


/**
 * Контейнер для {@link Command}
 */
public class CommandContainer {
    //основные команды
    private final ImmutableMap<String, Command> generalCommandMap;
    //команды для 3-х цеховых групп с рассылкой инцидентов
    private final ImmutableMap<String, Command> DepartmentCommandMap;
    //команды для приватного чата с ботом
    private final ImmutableMap<String, Command> privateChatCommandMap;
    private final Command unknownCommand;

    public CommandContainer(SendMessageService sendMessageService, ZabbixRestController zabbixRestController){

        generalCommandMap = ImmutableMap.<String, Command>builder()

                .put(UNKNOWN_USER.getCommandName(), new UnknownUserCommand(sendMessageService))
                .put(HELP.getCommandName(), new HelpAdminsGroupCommand(sendMessageService))
                .put(ZIP.getCommandName(), new ZipCommand(sendMessageService))
                .build();

        DepartmentCommandMap = ImmutableMap.<String, Command>builder()
                .put(CANCEL.getCommandName(), new CancelCommand(sendMessageService))
                .put(EDIT_INCIDENT.getCommandName(), new EditIncidentMessageCommand(sendMessageService))
                .put(HELP.getCommandName(), new HelpCommand(sendMessageService))
                .put(UPDATE.getCommandName(), new UpdateCommand(sendMessageService))
                .build();

        privateChatCommandMap = ImmutableMap.<String, Command>builder()
                .put(HELP.getCommandName(), new HelpPrivateMessageCommand(sendMessageService))
                .put(MENU.getCommandName(), new MenuCommand(sendMessageService))
                .put(START.getCommandName(), new StartCommand(sendMessageService))
                .put(PROBLEM.getCommandName(), new ProblemCommand(sendMessageService))
                .put(MENU_CHARTS.getCommandName(), new ChartsMenuCommand(sendMessageService))
                .put(GAMES.getCommandName(), new GamesMenuCommand(sendMessageService))
                .put(BACK.getCommandName(), new MenuCommand(sendMessageService))
                .put(INTERNET_PING.getCommandName(), new InternetPingCommand(sendMessageService, zabbixRestController))
                .put(CPU_SRV_ERP_2.getCommandName(), new SrvErpCpuCommand(sendMessageService, zabbixRestController))
                .put(PROXY_PING_COMMAND.getCommandName(), new ProxyPingCommand(sendMessageService, zabbixRestController))
                .put(COMMUTATOR_PING.getCommandName(), new CommutatorPingCommand(sendMessageService, zabbixRestController))
                .put(ERP_DISK_F.getCommandName(), new ErpTwoDiskSpaceCommand(sendMessageService, zabbixRestController))
                .put(SOCCER_COMMAND.getCommandName(), new SoccerCommand(sendMessageService))
                .put(BASKETBALL_COMMAND.getCommandName(), new BasketballCommand(sendMessageService))
                .put(DARTS_COMMAND.getCommandName(), new DartsCommand(sendMessageService))
                .put(SLOT_MACHINE_COMMAND.getCommandName(), new SlotMachineCommand(sendMessageService))
                .put(BOWLING_COMMAND.getCommandName(), new BowlingCommand(sendMessageService))
                .put(DICE_COMMAND.getCommandName(), new DiceCommand(sendMessageService))
                .put(WEATHER.getCommandName(), new WeatherMenuCommand(sendMessageService))
                .put(CURRENT_WEATHER.getCommandName(), new CurrentWeatherCommand(sendMessageService))
                .put(TEMP.getCommandName(), new TempCommand(sendMessageService))
                .build();

        unknownCommand = new UnknownCommand(sendMessageService);
    }



    public Command retrieveGeneralCommand(String commandIdentifier){
        return generalCommandMap.getOrDefault(commandIdentifier, unknownCommand);
    }

    public Command retrievePrivateChatCommand(String commandIdentifier){
        return privateChatCommandMap.get(commandIdentifier);
    }

    public Command retrieveDepartmentCommand(String commandIdentifier){
        return DepartmentCommandMap.getOrDefault(commandIdentifier, unknownCommand);
    }

    public boolean isPrivateChatCommandMapContainsCommand(String command){
        if(privateChatCommandMap.containsKey(command)) {
            return true;
        } else {
            return false;
        }
    }

}
