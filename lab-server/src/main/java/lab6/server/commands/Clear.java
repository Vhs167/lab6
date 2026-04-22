package lab6.server.commands;

import lab6.common.commands.CommandType;
import lab6.common.dto.Response;
import lab6.common.dto.Request;
import lab6.server.managers.CollectionManager;


/**
 * Команда 'clear' очищает все элементы коллекции
 */

public class Clear extends AbstractCommand {

    private final CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager) {
        super("clear", CommandType.NO_ARG, "очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        return collectionManager.clear();
    }
}
