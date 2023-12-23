package pairproject.foodmap.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
