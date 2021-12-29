package ru.energomera.zabbixbot.command.departments;

import org.junit.jupiter.api.DisplayName;
import ru.energomera.zabbixbot.command.AbstractCommandTest;
import ru.energomera.zabbixbot.command.Command;

import static ru.energomera.zabbixbot.command.CommandName.HELP;
import static ru.energomera.zabbixbot.command.departments.HelpCommand.HELP_MESSAGE;

@DisplayName("Unit-level testing for HelpCommand")
class HelpCommandTest extends AbstractCommandTest {

    @Override
    protected String getCommandName() {
        return HELP.getCommandName();
    }

    @Override
    protected String getCommandMessage() {
        return HELP_MESSAGE;
    }

    @Override
    protected Command getCommand() {
        return new HelpCommand(sendMessageService);
    }
}