package pairproject.foodmap.exception.exHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pairproject.foodmap.exception.DataAccessException;
import pairproject.foodmap.exception.DuplicateUserException;
import pairproject.foodmap.exception.FileOperationException;

@RestControllerAdvice
public class ExControllerAdvice {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResult> validExHandler(ValidationException e) {
        ErrorResult errorResult = new ErrorResult("BAD", "데이터 오류");
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ErrorResult> jsonProcessingExHandler(JsonProcessingException e) {
        ErrorResult errorResult = new ErrorResult("BAD", "JSON 파싱 오류");
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResult> DuplicateUserExHandler(DuplicateUserException e) {
        ErrorResult errorResult = new ErrorResult("BAD", "중복 회원 존재");
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResult> dataAccessExHandler(DataAccessException e) {
        ErrorResult errorResult = new ErrorResult("SERVER", "데이터 처리 오류");
        return new ResponseEntity<>(errorResult, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileOperationException.class)
    public ResponseEntity<ErrorResult> fileOperationExHandler(FileOperationException e) {
        ErrorResult errorResult = new ErrorResult("SERVER", "파일 처리 오류");
        return new ResponseEntity<>(errorResult, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
