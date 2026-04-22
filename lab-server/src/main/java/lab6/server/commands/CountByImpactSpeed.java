package lab6.server.commands;

import lab6.common.commands.CommandType;
import lab6.common.dto.Response;
import lab6.common.dto.Request;
import lab6.common.utils.Validator;
import lab6.server.managers.CollectionManager;

/**
 * Команада "count_by_impact_speed выводит количество элементов с заданным полем impactSpeed
 */

public class CountByImpactSpeed extends AbstractCommand {

    private final CollectionManager collectionManager;

    public CountByImpactSpeed(CollectionManager collectionManager) {
        super("count_by_impact_speed impactSpeed", CommandType.ONE_ARG, "вывести количество элементов, значение поля impactSpeed которых равно заданному");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        double impactSpeed = Validator.validateDoubleArg(request.getArgs(), getName());
        return collectionManager.countByImpactSpeed(impactSpeed);
    }
}
