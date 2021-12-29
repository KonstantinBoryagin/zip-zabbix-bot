package ru.energomera.zabbixbot.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.energomera.zabbixbot.controller.ZabbixRestController;
import ru.energomera.zabbixbot.service.SendMessageService;
import ru.energomera.zabbixbot.service.WeatherService;

@DisplayName("Unit-level testing for CommandContainer")
class CommandContainerTest {

    private CommandContainer commandContainer;

    @BeforeEach
    public void init() {
        SendMessageService sendMessageService = Mockito.mock(SendMessageService.class);
        ZabbixRestController zabbixRestController = Mockito.mock(ZabbixRestController.class);
        WeatherService weatherService = Mockito.mock(WeatherService.class);
        commandContainer = new CommandContainer(sendMessageService, zabbixRestController, weatherService);
    }

    @Test
    public void shouldGeneralCommandMapReturnUnknownCommand() {
        String unknownCommand = "/sdfkjsdf";

        Command command = commandContainer.retrieveGeneralCommand(unknownCommand);

        Assertions.assertEquals(UnknownCommand.class, command.getClass());
    }

    @Test
    public void shouldGetTheExistingGeneralCommand() {

    }

}