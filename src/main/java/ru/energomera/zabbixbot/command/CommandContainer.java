package ru.energomera.zabbixbot.command;

import com.google.common.collect.ImmutableMap;
import ru.energomera.zabbixbot.command.departments.CancelCommand;
import ru.energomera.zabbixbot.command.departments.HelpCommand;
import ru.energomera.zabbixbot.command.departments.Temp2Command;
import ru.energomera.zabbixbot.command.departments.UpdateCommand;
import ru.energomera.zabbixbot.command.privatechat.*;
import ru.energomera.zabbixbot.controller.ZabbixRestService;
import ru.energomera.zabbixbot.service.SendMessageService;

import static ru.energomera.zabbixbot.command.CommandName.*;


public class CommandContainer {
    private final ImmutableMap<String, Command> generalCommandMap;
    private final ImmutableMap<String, Command> DepartmentCommandMap;
    private final ImmutableMap<String, Command> privateChatCommandMap;
    private final Command unknownCommand;

    public CommandContainer(SendMessageService sendMessageService, ZabbixRestService zabbixRestService){

        generalCommandMap = ImmutableMap.<String, Command>builder()

                .put(UNKNOWN_USER.getCommandName(), new UnknownUserCommand(sendMessageService))
//                .put(CHART.getCommandName(), new ChartCommand(sendMessageService))
//                .put(TEMP.getCommandName(), new TempCommand(sendMessageService))
                .put(HELP.getCommandName(), new HelpAdminsGroupCommand(sendMessageService))
//                .put(TEMP3.getCommandName(), new Temp3Command(sendMessageService))
//                .put(TEMP_INLINE.getCommandName(), new TempInlineCommand(sendMessageService))
                .put(ZIP.getCommandName(), new ZipCommand(sendMessageService))
                .put(BUTTON.getCommandName(), new ButtonCommand(sendMessageService))

                .build();

        DepartmentCommandMap = ImmutableMap.<String, Command>builder()
                .put(CANCEL.getCommandName(), new CancelCommand(sendMessageService))
                .put(TEMP2.getCommandName(), new Temp2Command(sendMessageService))
                .put(HELP.getCommandName(), new HelpCommand(sendMessageService))
                .put(UPDATE.getCommandName(), new UpdateCommand(sendMessageService))
                .build();

        privateChatCommandMap = ImmutableMap.<String, Command>builder()
                .put(HELP.getCommandName(), new HelpPrivateMessageCommand(sendMessageService))
                .put(MENU.getCommandName(), new MenuCommand(sendMessageService))
                .put(START.getCommandName(), new StartCommand(sendMessageService))
                .put(PROBLEM.getCommandName(), new ProblemCommand(sendMessageService))
                .put(MENU_CHARTS.getCommandName(), new MenuChartsCommand(sendMessageService))
                .put(SLOT.getCommandName(), new SlotMachineCommand(sendMessageService))
                .put(BACK.getCommandName(), new MenuCommand(sendMessageService))
                .put(INTERNET_PING.getCommandName(), new InternetPingCommand(sendMessageService, zabbixRestService))
                .put(CPU_SRV_ERP_2.getCommandName(), new SrvErpCpuCommand(sendMessageService, zabbixRestService))
                .put(PROXY_PING_COMMAND.getCommandName(), new ProxyPingCommand(sendMessageService, zabbixRestService))
                .put(COMMUTATOR_PING.getCommandName(), new CommutatorPingCommand(sendMessageService, zabbixRestService))
                .put(ERP_DISK_F.getCommandName(), new ErpTwoDiskSpaceCommand(sendMessageService, zabbixRestService))
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
