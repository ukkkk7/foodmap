package pairproject.foodmap.exception;

public class DuplicateUserException extends RuntimeException{
    public DuplicateUserException(String message) {
        super(message);
    }
}
