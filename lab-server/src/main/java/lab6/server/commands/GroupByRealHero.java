package lab6.server.commands;

import lab6.common.dto.Response;
import lab6.common.dto.Request;
import lab6.server.managers.CollectionManager;

import java.util.Collections;

/**
 * Команда 'group_counting_by_real_hero' группирует элементы по значению поля realHero и выводит количество элементов в каждой группе
 */

public class GroupByRealHero extends AbstractCommand {

    private final CollectionManager collectionManager;


    public GroupByRealHero(CollectionManager collectionManager) {
        super("group_counting_by_real_hero", "сгруппировать элементы коллекции по значению поля realHero, вывести количество элементов в каждой группе");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        return new Response(Collections.emptyList(), "Количество настоящих героев и не героев: " + collectionManager.countByRealHero());
    }
}
