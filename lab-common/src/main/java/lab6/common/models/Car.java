package lab6.common.models;

import java.io.Serializable;

public class Car implements Serializable {
    private Boolean cool; //Поле может быть null

    public Car(Boolean cool) {
        this.cool = cool;
    }

    public Boolean getCool() {
        return cool;
    }

    @Override
    public String toString() {
        if (cool == null) {
            return "Car{cool=null}";
        } else {
            return "Car{cool=" + cool + "}";
        }
    }
}