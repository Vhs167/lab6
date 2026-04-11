package lab6.server.utils;

import lab6.common.exceptions.EnvironmentVariableNotSetException;
import lab6.common.models.*;
import lab6.common.utils.Validator;
import lab6.server.managers.CollectionManager;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.logging.Level;

public class CsvLoader {
    private final String fileName;
    private final CollectionManager collectionManager;

    public CsvLoader(CollectionManager collectionManager) throws EnvironmentVariableNotSetException {
        this.fileName = Validator.getCollectionFile();
        this.collectionManager = collectionManager;
    }


    public void loadFromFile() {
        try {
            Validator.validateFileReadable(fileName);

            try (Scanner scanner = new Scanner(new File(fileName))) {

                if (!scanner.hasNextLine()) return;
                scanner.nextLine();

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] tokens = line.split(",", -1);

                    try {
                        long id = Long.parseLong(tokens[0]);
                        String name = tokens[1];
                        int x = Integer.parseInt(tokens[2]);
                        float y = Float.parseFloat(tokens[3]);
                        LocalDateTime creationDate = LocalDateTime.parse(tokens[4]);
                        Boolean realHero = Boolean.parseBoolean(tokens[5]);
                        boolean hasToothpick = Boolean.parseBoolean(tokens[6]);
                        double impactSpeed = Double.parseDouble(tokens[7]);
                        String soundtrackName = tokens[8];
                        double minutesOfWaiting = Double.parseDouble(tokens[9]);
                        Mood mood = tokens[10].isEmpty() ? null : Mood.valueOf(tokens[10]);
                        Car car = new Car(tokens[11].isEmpty() ? null : Boolean.parseBoolean(tokens[11]));

                        HumanBeing human = new HumanBeing(
                                id,
                                name,
                                new Coordinates(x, y),
                                creationDate,
                                realHero,
                                hasToothpick,
                                impactSpeed,
                                soundtrackName,
                                minutesOfWaiting,
                                mood,
                                car
                        );

                        collectionManager.add(human);
                    } catch (Exception e) {
                        ServerLogger.logger.log(Level.SEVERE, "Ошибка чтения строки" + line, e);
                        System.err.println("Ошибка при чтении строки: " + line + " (" + e.getMessage() + ")");
                    }
                }
                ServerLogger.logger.info("Коллекция успешно добавлена");
            }
        } catch (Exception e) {
            ServerLogger.logger.log(Level.SEVERE, "Ошибка чтении файла" + fileName, e);
            throw new RuntimeException("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}
