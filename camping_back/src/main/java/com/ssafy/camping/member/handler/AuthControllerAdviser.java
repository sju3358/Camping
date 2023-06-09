package com.ssafy.camping.member.handler;

import com.ssafy.camping.member.exception.DuplicatedMemberIdException;
import com.ssafy.camping.member.exception.InvalidMemberPasswordException;
import com.ssafy.camping.member.exception.NullValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AuthControllerAdviser {

    @ExceptionHandler(InvalidMemberPasswordException.class)
    public ResponseEntity<Map<String,Object>> invalidPassword(InvalidMemberPasswordException e){
        Map<String,Object> token = new HashMap<>();
        token.put("error",e.getMessage());
        token.put("message","fail");
        HttpStatus status = HttpStatus.ACCEPTED;
        return new ResponseEntity<>(token,status);
    }

    @ExceptionHandler(NullValueException.class)
    public ResponseEntity<Map<String,Object>> nullValue(NullValueException e){
        Map<String,Object> token = new HashMap<>();
        token.put("error",e.getMessage());
        token.put("message","fail");
        HttpStatus status = HttpStatus.ACCEPTED;
        return new ResponseEntity<Map<String,Object>>(token,status);
    }

    @ExceptionHandler(DuplicatedMemberIdException.class)
    public ResponseEntity<Map<String,Object>> duplicatedMemberId(DuplicatedMemberIdException e){
        Map<String,Object> token = new HashMap<>();
        token.put("error",e.getMessage());
        token.put("message","fail");
        HttpStatus status = HttpStatus.ACCEPTED;
        System.out.println("a");
        return new ResponseEntity<Map<String,Object>>(token,status);
    }
}
