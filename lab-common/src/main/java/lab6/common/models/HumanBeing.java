package lab6.common.models;

import lab6.common.utils.DateUtils;
import lab6.common.exceptions.InvalidFieldException;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Класс человека
 */

public class HumanBeing implements Comparable<HumanBeing>, Serializable {

    private long id;
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private Boolean realHero;
    private boolean hasToothpick;
    private double impactSpeed;
    private String soundtrackName;
    private double minutesOfWaiting;
    private Mood mood;
    private Car car;

    public HumanBeing(
            long id,
            String name,
            Coordinates coordinates,
            LocalDateTime creationDate,
            Boolean realHero,
            boolean hasToothpick,
            double impactSpeed,
            String soundtrackName,
            double minutesOfWaiting,
            Mood mood,
            Car car
    ) throws InvalidFieldException {
        if (id <= 0) {
            throw new InvalidFieldException("id должен быть больше 0");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new InvalidFieldException("name не может быть пустым");
        }

        if (coordinates == null) {
            throw new InvalidFieldException("coordinates не может быть null");
        }

        if (creationDate == null) {
            throw new InvalidFieldException("creationDate не может быть null");
        }

        if (realHero == null) {
            throw new InvalidFieldException("realHero не может быть null");
        }

        if (soundtrackName == null) {
            throw new InvalidFieldException("soundtrackName не может быть null");
        }

        if (car == null) {
            throw new InvalidFieldException("car не может быть null");
        }
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.soundtrackName = soundtrackName;
        this.minutesOfWaiting = minutesOfWaiting;
        this.mood = mood;
        this.car = car;
    }

    public HumanBeing(
            String name,
            Coordinates coordinates,
            Boolean realHero,
            boolean hasToothpick,
            double impactSpeed,
            String soundtrackName,
            double minutesOfWaiting,
            Mood mood,
            Car car
    ) throws InvalidFieldException {

        if (coordinates == null) {
            throw new InvalidFieldException("coordinates не может быть null");
        }

        if (realHero == null) {
            throw new InvalidFieldException("realHero не может быть null");
        }

        if (soundtrackName == null) {
            throw new InvalidFieldException("soundtrackName не может быть null");
        }

        if (car == null) {
            throw new InvalidFieldException("car не может быть null");
        }

        this.name = name;
        this.coordinates = coordinates;
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.soundtrackName = soundtrackName;
        this.minutesOfWaiting = minutesOfWaiting;
        this.mood = mood;
        this.car = car;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public int compareTo(HumanBeing other) {
        return Double.compare(this.impactSpeed, other.impactSpeed);
    }

    public long getId() {
        return id;
    }

    public boolean getRealHero() {
        return realHero;
    }

    public double getImpactSpeed() {
        return impactSpeed;
    }

    public String getSoundtrackName() {
        return soundtrackName;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public boolean getHasToothpick() {
        return hasToothpick;
    }

    public double getMinutesOfWaiting() {
        return minutesOfWaiting;
    }

    public Mood getMood() {
        return mood;
    }

    public Car getCar() {
        return car;
    }

    @Override
    public String toString() {
        String info = "";
        info += "id: " + id + " {";
        info += "\n добавлен " + DateUtils.getDate();
        info += "\n имя: " + name;
        info += "\n координаты: " + coordinates;
        info += "\n настоящий герой: " + realHero;
        info += "\n есть зубная щетка: " + hasToothpick;
        info += "\n сила удара: " + impactSpeed;
        info += "\n название песни: " + soundtrackName;
        info += "\n минуты ожидания: " + minutesOfWaiting;
        info += "\n настроение: " + mood;
        info += "\n машина: " + car + "\n}";
        return info;
    }
}