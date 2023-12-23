package pairproject.foodmap.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
<<<<<<< HEAD
import pairproject.foodmap.exception.exHandler.ErrorResponse;
=======
>>>>>>> featuer/manageUser

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;
    private String message;

    public CustomException(String message, ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
