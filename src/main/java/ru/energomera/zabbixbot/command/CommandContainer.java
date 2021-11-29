package ru.energomera.zabbixbot.command;

import com.google.common.collect.ImmutableMap;
import ru.energomera.zabbixbot.service.SendMessageService;
import static ru.energomera.zabbixbot.command.CommandName.*;


public class CommandContainer {
    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendMessageService sendMessageService){

        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendMessageService))
//                .put(STOP.getCommandName(), new StopCommand(sendMessageService))
                .put(HELP.getCommandName(), new HelpCommand(sendMessageService))
                .put(ZABBIX.getCommandName(), new ZabbixCommand(sendMessageService))
                .put(PING.getCommandName(), new PingCommand(sendMessageService))
                .put(HISTORY.getCommandName(), new HistoryCommand(sendMessageService))
                .put(CPU_SRV_ERP_2.getCommandName(), new SrvErpCpuCommand(sendMessageService))
                .put("ZipCommand", new ZipCommand(sendMessageService))
//                .put(NO.getCommandName(), new NoCommand(sendMessageService))
                .build();

        unknownCommand = new UnknownCommand(sendMessageService);
    }

    public Command retrieveCommand(String commandIdentifier){
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}
