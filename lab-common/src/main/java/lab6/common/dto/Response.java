package lab6.common.dto;

import lab6.common.models.HumanBeing;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {
    private static final long serialVersionUID = 2L;
    private final List<HumanBeing> collection;
    private final String message;

    public Response(List<HumanBeing> collection, String message) {
        this.collection = collection;
        this.message = message;
    }

    public List<HumanBeing> getCollection() {
        return collection;
    }

    public String getMessage() {
        return message;
    }

    public boolean isEmpty() {
        return collection == null || collection.isEmpty();
    }
}
