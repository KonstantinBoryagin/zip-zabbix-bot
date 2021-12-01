package ru.energomera.zabbixbot.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.energomera.zabbixbot.zabbixapi.dto.history.HistoryRequest;
import ru.energomera.zabbixbot.zabbixapi.dto.history.HistoryResponse;
import ru.energomera.zabbixbot.zabbixapi.dto.history.HistoryResult;

public interface Command {

    void execute(Update update);

}
