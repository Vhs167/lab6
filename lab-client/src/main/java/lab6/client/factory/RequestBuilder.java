package lab6.client.factory;

import lab6.common.dto.HumanBeingRequest;
import lab6.common.dto.Request;
import lab6.common.exceptions.InvalidCommandException;
import lab6.common.exceptions.InvalidFieldException;
import lab6.common.utils.Validator;
import lab6.client.managers.IOManager;
import lab6.client.readers.HumanBeingInputReader;

import java.util.HashMap;
import java.util.Map;

public class RequestBuilder {
    private final IOManager ioManager;
    private final Map<String, Boolean> needsHuman = new HashMap<>();

    public RequestBuilder(IOManager ioManager) {
        this.ioManager = ioManager;
        needsHuman.put("add", true);
        needsHuman.put("add_if_min", true);
        needsHuman.put("update", true);
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
        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, args.length);
        try {
            Validator.validate(new Request(commandName, null, args));
        } catch (InvalidCommandException e) {
            ioManager.printError("Ошибка команды: " + e.getMessage());
            ioManager.println("Попробуйте снова");
            return null;
        }

        HumanBeingRequest humanBeingRequest = null;

        if (needsHuman.getOrDefault(commandName, false)) {
            try {
                HumanBeingInputReader reader = new HumanBeingInputReader(ioManager);
                humanBeingRequest = reader.read();
            } catch (InvalidFieldException e) {
                ioManager.printError("Ошибка ввода: " + e.getMessage());
                ioManager.println("Попробуйте снова");

                return null;
            }
        }
        return new Request(commandName, humanBeingRequest, args);
    }
}
