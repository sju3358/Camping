package com.ssafy.camping.member.util;

import org.springframework.stereotype.Component;
import java.util.regex.*;
@Component
public class RegexUtil {

    //숫자랑 영어만
    private final String regexPattern1 = "^[a-zA-Z0-9]*$";
    
    //숫자, 영어, 특수문자 10글자이상
    private final String regexPattern2 = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{10,}$";
    public boolean checkValidationRegisterId(String targetString){
        return Pattern.matches(regexPattern1, targetString);
    }

    public boolean checkValidationRegisterPassword(String targetString){
        return Pattern.matches(regexPattern2,targetString);
    }
}
