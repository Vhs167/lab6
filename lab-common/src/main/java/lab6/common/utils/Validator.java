package lab6.common.utils;

import lab6.common.commands.CommandType;
import lab6.common.dto.Request;
import lab6.common.exceptions.EnvironmentVariableNotSetException;
import lab6.common.exceptions.InvalidCommandException;
import lab6.common.models.HumanBeing;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Validator {
    private static final Map<String, Integer> commandArgCount = new HashMap<>();

    static {
        commandArgCount.put("add", 0);
        commandArgCount.put("add_if_min", 0);
        commandArgCount.put("clear", 0);
        commandArgCount.put("count_by_impact_speed", 1);
        commandArgCount.put("execute_script", 1);
        commandArgCount.put("exit", 0);
        commandArgCount.put("filter_greater_than_soundtrack_name", 1);
        commandArgCount.put("group_counting_by_real_hero", 0);
        commandArgCount.put("help", 0);
        commandArgCount.put("info", 0);
        commandArgCount.put("remove_by_id", 1);
        commandArgCount.put("remove_greater", 1);
        commandArgCount.put("remove_lower", 1);
        commandArgCount.put("save", 0);
        commandArgCount.put("show", 0);
        commandArgCount.put("update", 1);
    }

    public static void validate(Request request) throws InvalidCommandException {

        CommandType type = CommandType.fromName(request.getCommandName());

        if (type == null){
            throw new InvalidCommandException("Неизвестная команда: " + request.getCommandName());
        }

        int expectedArgs = type.getArgsCount();

        if (request.getArgs() == null) {
            if (expectedArgs != 0) {
                throw new InvalidCommandException("Команда " + type + " ожидает " + expectedArgs + " аргументов");
            }
            return;
        }

        if(request.getArgs().length != expectedArgs){
            throw new InvalidCommandException("Команда " + type + " ожидает " +
                    expectedArgs + " аргументов, получено: " + request.getArgs().length);
        }
    }

    public static String getCollectionFile() throws EnvironmentVariableNotSetException {
        String fileName = System.getenv("COLLECTION_FILE");
        if (fileName == null || fileName.isEmpty()) {
            throw new EnvironmentVariableNotSetException("Имя файла коллекции не задано в переменной окружения COLLECTION_FILE");
        }
        return fileName;
    }

    public static void validateId(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id должен быть больше 0");
        }
    }

    public static void validateExists(HumanBeing human) {
        if (human == null) {
            throw new IllegalArgumentException("Элемент с таким id не найден");
        }
    }

    public static void validateIsEmpty(Collection<HumanBeing> collection) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalStateException("Коллекция пуста");
        }
    }

    public static double validateDoubleArg(String[] args, String commandName) {
        try {
            return Double.parseDouble(args[0]);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("Аргумент команды " + commandName + " должен быть double");
        }
    }

    public static long validateLongArg(String[] args, String commandName) {
        try {
            return Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("Аргумент команды " + commandName + " должен быть long");
        }
    }

    public static void validateFileReadable(String fileName) throws InvalidCommandException {
        File file = new File(fileName);
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            throw new InvalidCommandException("Файл недоступен для чтения: " + fileName);
        }
    }

    public static void validateFileWritable(String fileName) throws InvalidCommandException {
        File file = new File(fileName);
        if (file.exists() && !file.canWrite()) {
            throw new InvalidCommandException("Файл недоступен для записи: " + fileName);
        }
    }
}
