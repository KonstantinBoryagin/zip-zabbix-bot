package ru.energomera.zabbixbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.energomera.zabbixbot.bot.ZabbixTelegramBot;

@SpringBootApplication
public class ZipzabbixbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZabbixTelegramBot.class, args);
	}

}
