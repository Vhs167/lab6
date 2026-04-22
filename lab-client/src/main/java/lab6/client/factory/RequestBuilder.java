package lab6.client.factory;

import lab6.common.dto.CommandInfo;
import lab6.common.dto.HumanBeingRequest;
import lab6.common.dto.Request;
import lab6.common.exceptions.InvalidCommandException;
import lab6.common.exceptions.InvalidFieldException;
import lab6.client.managers.IOManager;
import lab6.client.readers.HumanBeingInputReader;
import lab6.common.utils.Validator;

import java.util.Map;

public class RequestBuilder {
    private final IOManager ioManager;
    private final Map<String, CommandInfo> commands;


    public RequestBuilder(IOManager ioManager, Map<String, CommandInfo> commands) {
        this.ioManager = ioManager;
        this.commands = commands;
    }

    public Request buildRequest() {
        if (!ioManager.isScriptMode()) {
            ioManager.print("> ");
        }

        String line = ioManager.readLine();
        if (line == null || line.isEmpty()) {
            return null;
        }

        String[] parts = line.trim().split("\\s+");
        String commandName = parts[0];

        CommandInfo info = commands.get(commandName);

        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, args.length);

        if (info == null) {
            ioManager.printError("Неизвестная команда");
            return null;
        }

        HumanBeingRequest humanBeingRequest = null;


        if (info.isRequiresObject()) {
            try {
                HumanBeingInputReader reader = new HumanBeingInputReader(ioManager);
                humanBeingRequest = reader.read();
            } catch (InvalidFieldException e) {
                ioManager.printError("Ошибка ввода: " + e.getMessage());
                ioManager.println("Попробуйте снова");

                return null;
            }
        }
        Request request = new Request(commandName, humanBeingRequest, args);

        try {
            Validator.validate(info.getArgCount(), info.isRequiresObject(), request);
        } catch (InvalidCommandException e) {
            ioManager.printError(e.getMessage());
            return null;
        }
        return request;
    }
}
