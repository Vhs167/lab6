package lab6.server.managers;

import lab6.common.commands.CommandType;
import lab6.common.dto.CommandInfo;
import lab6.common.dto.Request;
import lab6.common.utils.Validator;
import lab6.server.commands.*;
import lab6.server.utils.CsvSaver;
import lab6.common.dto.Response;
import lab6.server.utils.ServerLogger;


import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;


/**
 * Класс оперирует командами
 */


public class CommandManager {

    private final Map<String,Command> commands = new HashMap<>();


    public CommandManager(CollectionManager collectionManager, CsvSaver csvSaver) {

        commands.put("show", new Show(collectionManager));
        commands.put("clear", new Clear(collectionManager));
        commands.put("remove_by_id", new RemoveById(collectionManager));
        commands.put("exit", new Exit(csvSaver));
        commands.put("group_by_real_hero", new GroupByRealHero(collectionManager));
        commands.put("count_by_impact_speed", new CountByImpactSpeed(collectionManager));
        commands.put("add", new Add(collectionManager));
        commands.put("update_by_id", new UpdateById(collectionManager));
        commands.put("add_if_min", new AddIfMin(collectionManager));
        commands.put("remove_greater", new RemoveGreater(collectionManager));
        commands.put("remove_lower", new RemoveLower(collectionManager));
        commands.put("filter_greater_then_soundtrack_name", new FilterGreaterSoundtrack(collectionManager));
        commands.put("info", new Info(collectionManager));
        commands.put("help", new Help(this));
        commands.put("get_commands", new GetCommands(this));
    }

    public Map<String, Command> getCommandsList() {
        return Collections.unmodifiableMap(commands);
    }

    public List<CommandInfo> getCommandsInfo(){
        return commands.entrySet().stream()
                .map(c -> new CommandInfo(
                        c.getKey(),
                        c.getValue().getCommandType()))
                .collect(Collectors.toList());
    }


    /**
     * Выполняет команду, введённую пользователем в виде строки
     */
    public Response executeCommand(Request request) {

        String commandName = request.getCommandName();

        if (commandName == null) {
            return new Response(Collections.emptyList(),
                    "Неизвестная команда: " + request.getCommandName());
        }

        Command command = commands.get(commandName);

        if (command == null) {
            return new Response(Collections.emptyList(), "Неизвестная команда: " + request.getCommandName());
        }
        try {
            CommandType type = command.getCommandType();

            Validator.validate(type.getArgsCount(), type.getRequiresObject(), request);
            Response response = command.execute(request);
            ServerLogger.logger.info("Команда выполнена: " + command.getName());

            return response;

        } catch (Exception e) {
            ServerLogger.logger.log(Level.WARNING, "Ошибка выполнения команды", e);
            return new Response(Collections.emptyList(), "Ошибка: " + e.getMessage());
        }

    }
}
