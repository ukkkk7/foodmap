package pairproject.foodmap.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pairproject.foodmap.exception.exHandler.ErrorResponse;

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
