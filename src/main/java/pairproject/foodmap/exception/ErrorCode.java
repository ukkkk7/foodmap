package pairproject.foodmap.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
public enum ErrorCode {

    INVALID_PARAMS(400, "InvalidParams", "필수데이터 누락, 또는 형식과 다른 데이터를 요청하셨습니다.")
    , UNAUTORIZED(401, "Unauthorized", "인증되지 않은 사용자입니다.")
    , INTERNAL_SERVER_ERROR(500, "InternalServer", "서버 에러입니다.")
    , NOT_FOUND(404, "NotFound", "존재하지 않는 데이터입니다.")
    ;

    ErrorCode (int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    ErrorCode (String code){
        this.code = code;
    }

    private int status;
    private String code;
    private String message;


}
