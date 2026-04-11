package lab6.server.commands;

import lab6.common.dto.Request;
import lab6.common.utils.DateUtils;
import lab6.common.dto.Response;
import lab6.server.managers.CollectionManager;

import java.util.Collections;

/**
 * Команда 'info' выводит информацию о коллекции
 */

public class Info extends AbstractCommand {

    private final CollectionManager collectionManager;

    public Info(CollectionManager collectionManager) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        StringBuilder sb = new StringBuilder();

        sb.append("Тип коллекци: ");
        sb.append(collectionManager.getCollectionType());
        sb.append("\n");

        sb.append("Дата инициализации: ");
        sb.append(DateUtils.getDate());
        sb.append("\n");

        sb.append("Количество элементов: ");
        sb.append(collectionManager.getSize());

        return new Response(Collections.emptyList(), sb.toString());
    }
}
