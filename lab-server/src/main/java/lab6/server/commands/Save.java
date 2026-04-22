package lab6.server.commands;

import lab6.common.commands.CommandType;
import lab6.common.dto.Request;
import lab6.common.utils.Validator;
import lab6.server.utils.CsvSaver;
import lab6.common.dto.Response;


/**
 * Команда 'save' сохраняет коллекцию в файл формата csv
 */

public class Save extends AbstractCommand {

    private final CsvSaver csvSaver;

    public Save(CsvSaver csvSaver) {
        super("save", CommandType.ONE_ARG,"сохранить коллекцию в файл");
        this.csvSaver = csvSaver;
    }

    @Override
    public Response execute(Request request) {
        String fileName = Validator.getCollectionFile();
        return csvSaver.save(fileName);
    }
}
