package lab6.server.utils;

import lab6.common.dto.Response;
import lab6.common.models.HumanBeing;
import lab6.common.utils.Validator;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;

public class CsvSaver {

    private final Collection<HumanBeing> collection;

    public CsvSaver(Collection<HumanBeing> collection) {
        this.collection = collection;
    }

    public Response save(String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            Validator.validateFileWritable(fileName);
            writer.println("id,name,coordX,coordY,creationDate,realHero,hasToothpick,impactSpeed,soundtrackName,minutesOfWaiting,mood,carCool");

            for (HumanBeing h : collection) {
                writer.println(h.getId() + "," +
                        h.getName() + "," +
                        h.getCoordinates().getX() + "," +
                        h.getCoordinates().getY() + "," +
                        h.getCreationDate() + "," +
                        h.getRealHero() + "," +
                        h.getHasToothpick() + "," +
                        h.getImpactSpeed() + "," +
                        h.getSoundtrackName() + "," +
                        h.getMinutesOfWaiting() + "," +
                        (h.getMood() != null ? h.getMood() : "") + "," +
                        (h.getCar() != null && h.getCar().getCool() != null ? h.getCar().getCool() : ""));
            }
            ServerLogger.logger.info("Коллекция сохранена в " + fileName);
            return new Response(Collections.emptyList(), "Коллекция успешно сохранена в " + fileName);

        } catch (Exception e) {
            ServerLogger.logger.log(Level.SEVERE, "Ошибка пи сохранении коллекции", e);
            return new Response(Collections.emptyList(), "Ошибка при сохранении: " + e.getMessage());
        }
    }
}
