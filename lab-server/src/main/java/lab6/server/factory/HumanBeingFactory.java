package lab6.server.factory;

import lab6.common.dto.HumanBeingRequest;
import lab6.common.exceptions.InvalidFieldException;

import lab6.common.models.HumanBeing;

public class HumanBeingFactory {
    public static HumanBeing create(HumanBeingRequest request) throws InvalidFieldException {
        if (request.coordinates == null) {
            throw new InvalidFieldException("Coordinates не может быть null");
        }
        if (request.car == null) {
            throw new InvalidFieldException("Car не может быть null");
        }

        return new HumanBeing(
                request.name,
                request.coordinates,
                request.realHero,
                request.hasToothpick,
                request.impactSpeed,
                request.soundtrackName,
                request.minutesOfWaiting,
                request.mood,
                request.car
        );
    }
}
