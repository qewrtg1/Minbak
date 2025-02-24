package com.minbak.web.messages;

import lombok.Getter;

@Getter
public class ErrorMessage {

    // 기본적인 오류 메시지 정의
    public static final String NULL_VALUE_ERROR = "필수 값이 없습니다. 값을 입력해주세요.";
    public static final String USER_ALREADY_EXISTS = "이미 존재하는 이메일입니다.";
    public static final String USER_NOT_FOUND = "해당 사용자를 찾을 수 없습니다.";
    public static final String INVALID_EMAIL_FORMAT = "이메일 형식이 올바르지 않습니다.";
    public static final String SEARCH_ERROR = "검색 중 오류가 발생했습니다.";
    public static final String FILTER_ERROR = "필터링 오류가 발생했습니다.";
    public static final String CREATE_ERROR = "회원 생성에 실패했습니다.";

    // 매개변수에 따라 다른 오류 메시지를 반환하는 메서드
    public static String getErrorMessage(String errorType, String additionalInfo) {
        switch (errorType) {
            case "nullError":
                return NULL_VALUE_ERROR;
            case "userExists":
                return USER_ALREADY_EXISTS;
            case "userNotFound":
                return USER_NOT_FOUND;
            case "invalidEmail":
                return INVALID_EMAIL_FORMAT;
            case "searchError":
                return SEARCH_ERROR;
            case "filterError":
                return FILTER_ERROR;
            case "createError":
                return CREATE_ERROR;
            default:
                return "알 수 없는 오류가 발생했습니다.";
        }
    }

    // 오류 유형과 추가 정보를 함께 출력할 수 있도록
    public static String getDetailedErrorMessage(String errorType, String additionalInfo) {
        switch (errorType) {
            case "nullError":
                return NULL_VALUE_ERROR + " 추가 정보: " + additionalInfo;
            case "userExists":
                return USER_ALREADY_EXISTS + " 이메일: " + additionalInfo;
            case "userNotFound":
                return USER_NOT_FOUND + " 이메일: " + additionalInfo;
            case "invalidEmail":
                return INVALID_EMAIL_FORMAT + " 이메일: " + additionalInfo;
            case "searchError":
                return SEARCH_ERROR + " 검색 조건: " + additionalInfo;
            case "filterError":
                return FILTER_ERROR + " 필터: " + additionalInfo;
            case "createError":
                return CREATE_ERROR + " 오류: " + additionalInfo;
            default:
                return "알 수 없는 오류가 발생했습니다.";
        }
    }
}


    //--------------------------------------구현 예시------------------------------------------
//public String registerUser(String email) {
//    if (email == null || email.isEmpty()) {
//        // 널 값 오류 처리
//        return ErrorMessage.getDetailedErrorMessage("nullError", "이메일 값이 null입니다.");
//    }
//    if (userService.checkIfUserExists(email)) {
//        // 이미 존재하는 이메일 오류 처리
//        return ErrorMessage.getDetailedErrorMessage("userExists", email);
//    }
//
//    // 사용자 등록 처리
//    try {
//        userService.registerUser(email);
//        return "회원가입 성공!";
//    } catch (Exception e) {
//        // 회원가입 오류 처리
//        return ErrorMessage.getDetailedErrorMessage("createError", e.getMessage());
//    }
//}
