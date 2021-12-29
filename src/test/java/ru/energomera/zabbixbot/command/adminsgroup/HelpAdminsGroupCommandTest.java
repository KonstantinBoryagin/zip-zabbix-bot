package ru.energomera.zabbixbot.command.adminsgroup;

import org.junit.jupiter.api.DisplayName;
import ru.energomera.zabbixbot.command.AbstractCommandTest;
import ru.energomera.zabbixbot.command.Command;

import static org.junit.jupiter.api.Assertions.*;
import static ru.energomera.zabbixbot.command.CommandName.HELP;
import static ru.energomera.zabbixbot.command.adminsgroup.HelpAdminsGroupCommand.HELP_MESSAGE_FOR_ADMINS_GROUP;

@DisplayName("Unit-level testing for HelpAdminsGroupCommandTest")
class HelpAdminsGroupCommandTest extends AbstractCommandTest {

    @Override
    protected String getCommandName() {
        return HELP.getCommandName();
    }

    @Override
    protected String getCommandMessage() {
        return HELP_MESSAGE_FOR_ADMINS_GROUP;
    }

    @Override
    protected Command getCommand() {
        return new HelpAdminsGroupCommand(sendMessageService);
    }
}