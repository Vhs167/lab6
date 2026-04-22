package lab6.server.commands;

import lab6.common.commands.CommandType;
import lab6.common.dto.Response;
import lab6.common.dto.Request;
import lab6.common.utils.Validator;
import lab6.server.factory.HumanBeingFactory;
import lab6.server.managers.CollectionManager;
import lab6.common.models.HumanBeing;

/**
 * Команда 'update' обновляет элемент коллекции по заданному id
 */

public class UpdateById extends AbstractCommand {

    private final CollectionManager collectionManager;

    public UpdateById(CollectionManager collectionManager) {
        super("update id {element}", CommandType.ONE_ARG_WITH_OBJECT, "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        long id = Validator.validateLongArg(request.getArgs(), getName());
        Validator.validateId(id);

        HumanBeing oldHuman = collectionManager.findById(id);
        Validator.validateExists(oldHuman);

        HumanBeing newHuman = HumanBeingFactory.create(request.getHumanBeingRequest());
        return collectionManager.updateById(id, newHuman);
    }
}
