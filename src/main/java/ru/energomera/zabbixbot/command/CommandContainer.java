package ru.energomera.zabbixbot.command;

import com.google.common.collect.ImmutableMap;
import ru.energomera.zabbixbot.command.menu.MenuChartsCommand;
import ru.energomera.zabbixbot.command.menu.DiceCommand;
import ru.energomera.zabbixbot.command.menu.MenuCommand;
import ru.energomera.zabbixbot.service.SendMessageService;

import static ru.energomera.zabbixbot.command.CommandName.*;


public class CommandContainer {
    private final ImmutableMap<String, Command> commandMap;
    private final ImmutableMap<String, Chart> chartMap;
    private final Command unknownCommand;

    public CommandContainer(SendMessageService sendMessageService){

        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendMessageService))
//                .put(STOP.getCommandName(), new StopCommand(sendMessageService))
                .put(HELP.getCommandName(), new HelpCommand(sendMessageService))
                .put(PROXY_PING_COMMAND.getCommandName(), new ProxyPingCommand(sendMessageService))
                .put(CPU_SRV_ERP_2.getCommandName(), new SrvErpCpuCommand(sendMessageService))
                .put(CHART.getCommandName(), new ChartCommand(sendMessageService))
                .put(UPDATE.getCommandName(), new UpdateCommand(sendMessageService))
                .put(YANDEX.getCommandName(), new YandexPingCommand(sendMessageService))
                .put(MENU.getCommandName(), new MenuCommand(sendMessageService))
                .put(DICE.getCommandName(), new DiceCommand(sendMessageService))
                .put(MENU_CHARTS.getCommandName(), new MenuChartsCommand(sendMessageService))
                .put("ZipCommand", new ZipCommand(sendMessageService))
//                .put(NO.getCommandName(), new NoCommand(sendMessageService))
                .build();

        unknownCommand = new UnknownCommand(sendMessageService);

        chartMap = ImmutableMap.<String, Chart>builder()
                .put(PROXY_PING_COMMAND.getCommandName(), new ProxyPingCommand(sendMessageService))
                .put(YANDEX.getCommandName(), new YandexPingCommand(sendMessageService))
                .build();

    }



    public Command retrieveCommand(String commandIdentifier){
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }

    public Chart retrieveChart(String commandIdentifier){
        return chartMap.get(commandIdentifier);
    }

}
