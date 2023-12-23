package pairproject.foodmap.exception.exHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pairproject.foodmap.exception.CustomException;
import pairproject.foodmap.exception.ErrorCode;

public class GlobalExControllerAdvice {

    @ExceptionHandler(CustomException.class)
    ResponseEntity<ErrorResponse> customExhandler(CustomException e){
        ErrorCode errorCode;
        HttpStatus httpStatus = resolveHttpStatus(e.getErrorCode());
        ErrorResponse errorResponse = new ErrorResponse(String.valueOf(e.getErrorCode()), e.getMessage());
        return new ResponseEntity<>(errorResponse, httpStatus);
    }



    private HttpStatus resolveHttpStatus(ErrorCode errorCode){

        switch (errorCode){
            case NOT_FOUND ->{ return HttpStatus.NOT_FOUND;}
            case UNAUTORIZED -> {return HttpStatus.UNAUTHORIZED;}
            case INVALID_PARAMS -> {return HttpStatus.BAD_REQUEST;}
            default -> {return HttpStatus.INTERNAL_SERVER_ERROR;}

        }

    }

}
