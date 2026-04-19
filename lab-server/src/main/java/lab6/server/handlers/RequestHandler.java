package lab6.server.handlers;

import lab6.common.dto.Response;
import lab6.common.dto.Request;
import lab6.server.managers.CommandManager;

public class RequestHandler {

    private final CommandManager commandManager;

    public RequestHandler(CommandManager commandmanager) {
        this.commandManager = commandmanager;
    }

    public Response handle(Request request){
        return commandManager.executeCommand(request);

    }
}
