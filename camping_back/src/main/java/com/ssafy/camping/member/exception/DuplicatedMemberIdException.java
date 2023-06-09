package com.ssafy.camping.member.exception;

public class DuplicatedMemberIdException extends RuntimeException{

    public DuplicatedMemberIdException() {
        super("등록된 ID가 이미 존재합니다.");
    }

    public DuplicatedMemberIdException(String message) {
        super(message);
    }
}
