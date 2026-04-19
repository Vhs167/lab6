package lab6.server.commands;

import lab6.common.dto.Response;
import lab6.common.dto.Request;
import lab6.server.managers.CommandManager;

import java.util.Collections;
import java.util.Map;

/**
 * Команда 'help' выводит справку по командам
 */


public class Help extends AbstractCommand {

    private final CommandManager commandManager;

    public Help(CommandManager commandManager) {
        super("help", "вывести справку по доступным командам");
        this.commandManager = commandManager;
    }

    @Override
    public Response execute(Request request) {
        StringBuilder sb = new StringBuilder();
        sb.append("Список доступных команд: \n");

        for (Map.Entry<String, Command> entry : commandManager.getCommandsList().entrySet()) {
            String name = entry.getKey();
            Command command = entry.getValue();

            sb.append("-");
            sb.append(name);
            sb.append(" : ");
            sb.append(command.getDescription());
            sb.append("\n");

        }
        return new Response(Collections.emptyList(), sb.toString());
    }
}
