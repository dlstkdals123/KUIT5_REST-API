package kuit.baemin.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BaseResponseStatus {

    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    DUPLICATED_EMAIL(false, 2001, "중복된 이메일 입니다."),
    NON_MATCH_PASSWORD(false, 2002, "비밀번호가 일치하지 않습니다."),
    DUPLICATED_NICKNAME(false, 2003, "중복된 닉네임입니다."),

    NON_MATCH_CREDENTIALS(false, 3001, "아이디 또는 비밀번호가 잘못되었습니다."),
    USER_NOT_FOUND(false, 3002, "해당 회원을 찾을 수 없습니다.");

    private final boolean isSuccess;
    private final int responseCode;
    private final String responseMessage;

}
