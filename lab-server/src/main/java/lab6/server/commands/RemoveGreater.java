package lab6.server.commands;


import lab6.common.commands.CommandType;
import lab6.common.dto.Response;
import lab6.common.dto.Request;
import lab6.common.utils.Validator;
import lab6.server.managers.CollectionManager;


/**
 * Команда 'remove_greater' удаляет все элементы коллекции, превыщающие заданное значение поля impactSpeed
 */

public class RemoveGreater extends AbstractCommand {

    private final CollectionManager collectionManager;

    public RemoveGreater(CollectionManager collectionManager) {
        super("remove_greater {element}", CommandType.ONE_ARG ,"удалить из коллекции все элементы, превышающие заданный");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        double impactSpeed = Validator.validateDoubleArg(request.getArgs(), getName());
        return collectionManager.removeGreater(impactSpeed);
    }
}
