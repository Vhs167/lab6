package lab6.common.models;

import lab6.common.exceptions.InvalidFieldException;

import java.io.Serializable;

/**
 * Класс координат
 */

public class Coordinates implements Serializable {
    private Integer x; //Значение поля должно быть больше -213, Поле не может быть null
    private Float y; //Поле не может быть null

    public Coordinates(Integer x, Float y) throws InvalidFieldException {

        if (x == null) {
            throw new InvalidFieldException("x не может быть null");
        }
        if (x <= -213) {
            throw new InvalidFieldException("x должен быть больше -213");
        }
        if (y == null) {
            throw new InvalidFieldException("y не может быть null");
        }
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String toString() {
        return "x=" + x + " y=" + y;
    }
}
