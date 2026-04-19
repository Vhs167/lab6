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

    private final Map<CommandType,Command> commands = new HashMap<>();


    public CommandManager(CollectionManager collectionManager, CsvSaver csvSaver) {

        commands.put(CommandType.SHOW, new Show(collectionManager));
        commands.put(CommandType.CLEAR, new Clear(collectionManager));
        commands.put(CommandType.REMOVE_BY_ID, new RemoveById(collectionManager));
        commands.put(CommandType.EXIT, new Exit(csvSaver));
        commands.put(CommandType.GROUP_BY_REAL_HERO, new GroupByRealHero(collectionManager));
        commands.put(CommandType.COUNT_BY_IMPACT_SPEED, new CountByImpactSpeed(collectionManager));
        commands.put(CommandType.ADD, new Add(collectionManager));
        commands.put(CommandType.UPDATE_BY_ID, new UpdateById(collectionManager));
        commands.put(CommandType.ADD_IF_MIN, new AddIfMin(collectionManager));
        commands.put(CommandType.REMOVE_GREATER, new RemoveGreater(collectionManager));
        commands.put(CommandType.REMOVE_LOWER, new RemoveLower(collectionManager));
        commands.put(CommandType.FILTER_GREATER_THAN_SOUNDTRACK_NAME, new FilterGreaterSoundtrack(collectionManager));
        commands.put(CommandType.INFO, new Info(collectionManager));
        commands.put(CommandType.HELP, new Help(this));
        commands.put(CommandType.GET_COMMANDS, new GetCommands(this));
    }

    public Map<String, Command> getCommandsList() {
        return commands.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().getName(),
                        Map.Entry::getValue
                ));
    }

    public List<CommandInfo> getCommands(){
        List<CommandInfo> list = new ArrayList<>();

        for(CommandType type : CommandType.values()){
            list.add(new CommandInfo(type));
        }
        return list;
    }

    public List<CommandInfo> getCommandsInfo(){
        return Arrays.stream(CommandType.values()).map(CommandInfo::new).collect(Collectors.toList());
    }


    /**
     * Выполняет команду, введённую пользователем в виде строки
     */
    public Response executeCommand(Request request) {

        CommandType type = CommandType.fromName(request.getCommandName());

        if (type == null) {
            return new Response(Collections.emptyList(),
                    "Неизвестная команда: " + request.getCommandName());
        }

        Command command = commands.get(type);

        if (command == null) {
            return new Response(Collections.emptyList(), "Неизвестная команда: " + request.getCommandName());
        }
        try {
            Validator.validate(request);
            Response response = command.execute(request);
            ServerLogger.logger.info("Команда выполнена: " + command.getName());

            return response;

        } catch (Exception e) {
            ServerLogger.logger.log(Level.WARNING, "Ошибка выполнения команды", e);
            return new Response(Collections.emptyList(), "Ошибка: " + e.getMessage());
        }

    }
}
