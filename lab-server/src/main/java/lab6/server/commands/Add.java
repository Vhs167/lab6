package lab6.server.commands;

import lab6.common.commands.CommandType;
import lab6.common.dto.Request;
import lab6.common.dto.Response;
import lab6.server.factory.HumanBeingFactory;
import lab6.server.managers.CollectionManager;
import lab6.common.models.HumanBeing;

/**
 * Команда "add". Добавляет новый элемент в коллекцию.
 */

public class Add extends AbstractCommand {
    private final CollectionManager collectionManager;


    public Add(CollectionManager collectionManager) {
        super("add", CommandType.WITH_OBJECT, "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
    }


    @Override
    public Response execute(Request request) {
        HumanBeing human = HumanBeingFactory.create(request.getHumanBeingRequest());
        return collectionManager.add(human);
    }
}
