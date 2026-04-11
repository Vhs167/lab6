package lab6.server.commands;

import lab6.common.dto.Response;
import lab6.common.dto.Request;
import lab6.server.managers.CollectionManager;


/**
 * Команда 'filter_greater_than_soundtrack_name' выводит элементы, значение поля soundtrackName которых больше заданного
 */

public class FilterGreaterSoundtrack extends AbstractCommand {

    private final CollectionManager collectionManager;

    public FilterGreaterSoundtrack(CollectionManager collectionManager) {
        super("filter_greater_than_soundtrack_name soundtrackName", "вывести элементы, значение поля soundtrackName которых больше заданного");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        String soundtrack = request.getArgs()[0];
        return collectionManager.filterGreaterThanSoundtrack(soundtrack);
    }
}
