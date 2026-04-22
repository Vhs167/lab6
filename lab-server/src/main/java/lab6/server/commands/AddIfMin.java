package lab6.server.commands;

import lab6.common.commands.CommandType;
import lab6.common.dto.Request;
import lab6.common.dto.Response;
import lab6.server.factory.HumanBeingFactory;
import lab6.server.managers.CollectionManager;
import lab6.common.models.HumanBeing;

/**
 * Команда 'add_if_min". Добавляет элемент в коллекцию, если его ImpactSpeed минимальный
 *
 * @author mikhail
 */


public class AddIfMin extends AbstractCommand {

    private final CollectionManager collectionManager;

    public AddIfMin(CollectionManager collectionManager) {
        super("add_if_min {element}", CommandType.WITH_OBJECT, "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        HumanBeing human = HumanBeingFactory.create(request.getHumanBeingRequest());
        return collectionManager.addIfMin(human);
    }
}
