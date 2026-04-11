package lab6.server.commands;


import lab6.common.dto.Response;
import lab6.common.dto.Request;
import lab6.common.utils.Validator;
import lab6.server.managers.CollectionManager;


/**
 * Команда 'remove_by_id' удаляет элемент из коллекции по значению его id
 */

public class RemoveById extends AbstractCommand {

    private final CollectionManager collectionManager;


    public RemoveById(CollectionManager collectionManager) {
        super("remove_by_id id", "удалить элемент из коллекции по его id");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {

        long id = Validator.validateLongArg(request.getArgs(), getName());
        Validator.validateId(id);
        return collectionManager.removeById(id);
    }
}
