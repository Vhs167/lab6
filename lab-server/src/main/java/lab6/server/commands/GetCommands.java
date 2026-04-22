package lab6.server.commands;

import lab6.common.commands.CommandType;
import lab6.common.dto.Request;
import lab6.common.dto.Response;
import lab6.server.managers.CommandManager;

public class GetCommands extends AbstractCommand implements Command{

    private final CommandManager commandManager;

    public GetCommands(CommandManager commandManager){
        super("get_commands", CommandType.NO_ARG, "Возвращает информацию о командах");
        this.commandManager = commandManager;
    }

    @Override
    public Response execute(Request request) {
        return new Response(commandManager.getCommandsInfo(),"commands");
    }
}
