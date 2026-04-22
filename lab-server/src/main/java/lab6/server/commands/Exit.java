package lab6.server.commands;


import lab6.common.commands.CommandType;
import lab6.common.dto.Response;
import lab6.common.dto.Request;
import lab6.common.utils.Validator;
import lab6.server.utils.CsvSaver;


import java.util.Collections;

/**
 * Команда 'exit' завершает программу без сохранения коллекции в файл
 */

public class Exit extends AbstractCommand {


    private final CsvSaver csvSaver;


    public Exit(CsvSaver csvSaver) {
        super("exit", CommandType.NO_ARG ,"завершить программу (без сохранения в файл)");
        this.csvSaver = csvSaver;

    }

    @Override
    public Response execute(Request request) {
        String fileName = Validator.getCollectionFile();

        csvSaver.save(fileName);
        return new Response(Collections.emptyList(), "Завершение программы");
    }
}
