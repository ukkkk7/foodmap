package pairproject.foodmap.exception.exHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ErrorResult {
    private LocalDateTime timestamp;
    private String message;
    private String code;

    public ErrorResult() {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResult(String code, String message) {
        this.timestamp = LocalDateTime.now();
        this.code = code;
        this.message = message;
    }
}
