package lv.wings.exception.validation;

public class InvalidIdException extends InvalidParameterException {
    public InvalidIdException(Integer idValue) {
        super("id", idValue + "", false);
    }

    public InvalidIdException(String idName, Integer idValue) {
        super(idName, idValue + "", false);
    }
}
