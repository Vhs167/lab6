package lab6.server.commands;

import lab6.common.dto.Response;
import lab6.common.dto.Request;
import lab6.common.utils.Validator;
import lab6.server.managers.CollectionManager;


/**
 * Команда 'remove_lower' удаляет все элементы коллекции, не превыщающие заданное значение поля impactSpeed
 */

public class RemoveLower extends AbstractCommand {

    private final CollectionManager collectionManager;


    public RemoveLower(CollectionManager collectionManager) {
        super("remove_lower {element}", "удалить из коллекции все элементы, меньшие, чем заданный");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        double impactSpeed = Validator.validateDoubleArg(request.getArgs(), getName());
        return collectionManager.removeLower(impactSpeed);
    }
}

