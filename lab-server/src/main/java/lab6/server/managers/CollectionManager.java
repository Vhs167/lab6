package lab6.server.managers;

import lab6.common.dto.Response;
import lab6.common.models.HumanBeing;

import lab6.server.utils.ServerLogger;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Класс оперирующий коллекцией
 */

public class CollectionManager {

    private LinkedHashSet<HumanBeing> collection = new LinkedHashSet<>();
    private long nextId = 0;


    public LinkedHashSet<HumanBeing> getCollection() {
        return collection;
    }

    public Response getAll() {
        List<HumanBeing> result = collection.stream().sorted(Comparator
                        .comparing((HumanBeing h) -> h.getCoordinates().getX())
                        .thenComparing(h -> h.getCoordinates().getY()))
                .collect(Collectors.toList());

        String message = result.isEmpty() ? "Коллекция пуста! " : "Элементы коллекции: ";

        return new Response(result, message);
    }

    public Response removeById(long id) {
        List<HumanBeing> removed = collection.stream()
                .filter(h -> h.getId() == id)
                .collect(Collectors.toList());

        boolean anyRemoved = collection.removeIf(h -> h.getId() == id);

        String message = anyRemoved ? "Объект удален: " : "Объект не найден! ";
        ServerLogger.logger.info("Удален объект с id: " + id);
        return new Response(removed, message);
    }

    public Response clear() {
        collection.clear();
        ServerLogger.logger.info("Коллекция очищена");
        return new Response(Collections.emptyList(), "Коллекция очищена");
    }

    public boolean isEmpty() {
        return collection.isEmpty();
    }

    public Response updateById(long id, HumanBeing newHuman) {
        HumanBeing oldHuman = findById(id);

        if (oldHuman == null) return new Response(Collections.emptyList(), "Элемент с таким id не найден");

        newHuman.setId(oldHuman.getId());
        newHuman.setCreationDate(oldHuman.getCreationDate());

        collection.remove(oldHuman);
        collection.add(newHuman);
        ServerLogger.logger.info("Объект с id: " + id + " добавлен");
        return new Response(Collections.singletonList(newHuman), "Объект успешно обновлен");
    }

    public void initializeHuman(HumanBeing human) {
        if (human.getId() < nextId) {
            human.setId(nextId);
            nextId++;
        } else if (human.getId() >= nextId) {
            nextId = human.getId() + 1;
        }
        human.setCreationDate(LocalDateTime.now());
    }

    public Response add(HumanBeing human) {
        initializeHuman(human);
        collection.add(human);
        ServerLogger.logger.info("В коллекцию добален объект \n" + human);
        return new Response(Collections.singletonList(human), "Объект успешно добавлен");
    }

    public HumanBeing findById(long id) {
        return collection.stream()
                .filter(h -> h.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Response removeGreater(double impactSpeed) {

        List<HumanBeing> removed = collection.stream()
                .filter(h -> h.getImpactSpeed() > impactSpeed)
                .collect(Collectors.toList());

        boolean anyRemoved = collection.removeIf(h -> h.getImpactSpeed() > impactSpeed);

        String message = anyRemoved ? "Удалено объектов: " + removed.size() : "Элементы с ImpactSpeed больше " + impactSpeed + " не найдены";

        ServerLogger.logger.info("Удалены объекты: \n" + removed);
        return new Response(removed, message);
    }

    public Response removeLower(double impactSpeed) {
        List<HumanBeing> removed = collection.stream()
                .filter(h -> h.getImpactSpeed() < impactSpeed)
                .collect(Collectors.toList());

        boolean anyRemoved = collection.removeIf(h -> h.getImpactSpeed() < impactSpeed);

        String message = anyRemoved ? "Удалено объектов: " + removed.size() : "Элементы с ImpactSpeed меньше " + impactSpeed + " не найдены";

        ServerLogger.logger.info("Удалены объекты: \n" + removed);
        return new Response(removed, message);
    }

    public Map<Boolean, Long> countByRealHero() {
        return collection.stream()
                .collect(Collectors.groupingBy(
                        HumanBeing::getRealHero,
                        Collectors.counting()));
    }

    public Response filterGreaterThanSoundtrack(String soundtrack) {
        List<HumanBeing> filtered = collection.stream()
                .filter(h -> h.getSoundtrackName() != null &&
                        h.getSoundtrackName().compareToIgnoreCase(soundtrack) > 0)
                .collect(Collectors.toList());

        String message = filtered.isEmpty() ?
                "Элементы с заданным soundtrackName не найдены" :
                "Найденные элементы: ";

        return new Response(filtered, message);
    }

    public Response countByImpactSpeed(double impactSpeed) {
        long count = collection.stream()
                .filter(h -> Double.compare(h.getImpactSpeed(), impactSpeed) == 0)
                .count();

        String message = count != 0 ?
                "Количество человек с ImpactSpeed: " + impactSpeed + " равно " + count :
                "Таких элементов не найдено";

        return new Response(Collections.emptyList(), message);
    }

    public Response addIfMin(HumanBeing human) {
        HumanBeing min = collection.stream()
                .min(Comparator.comparingDouble(HumanBeing::getImpactSpeed))
                .orElse(null);

        if (min == null || human.getImpactSpeed() < min.getImpactSpeed()) {
            add(human);
            ServerLogger.logger.info("В коллекцию добален объект \n" + human.toString());
            return new Response(Collections.singletonList(human), "Успешно добавлено");
        }
        return new Response(Collections.emptyList(), "Не добавлено");
    }

    public String getCollectionType() {
        return collection.getClass().getName();
    }

    public int getSize() {
        return collection.size();
    }
}
