package com.ssafy.camping.member.controller;


import com.ssafy.camping.member.dto.Member;
import com.ssafy.camping.member.model.service.JwtTokenService;
import com.ssafy.camping.member.model.service.MemberService;
import com.ssafy.camping.member.model.service.NaverMemberService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/naver")
@RestController
public class NaverAuthController {

    private final String CLIENT_ID = "0jXgPVDyLQu_ekRssB20";
    private final String CLIENT_SECRET = "3MjVF30KIu";
    
    private final NaverMemberService naverMemberService;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public NaverAuthController(NaverMemberService naverMemberService, JwtTokenService jwtTokenService){
        this.naverMemberService = naverMemberService;
        this.jwtTokenService = jwtTokenService;
    }


    @PostMapping("/auth")
    public ResponseEntity<Map<String,Object>> naverLoginRequest(@RequestBody Map<String, String> param) throws Exception {

        System.out.println("네이버로그인");

        String code = param.get("code").toString();
        String state = param.get("state").toString();

        HttpEntity<MultiValueMap<String, String>> AuthCodeRequest = generateAuthCodeRequest(code,state);
        ResponseEntity<String> AuthCodeResponse = requestAccessToken(AuthCodeRequest);

        String jsonData =  AuthCodeResponse.getBody().toString();

        Map<String,String> tokenInfo = getTokenInfo(jsonData);

        String NaverAccessToken = tokenInfo.get("access_token");
        String NaverRefreshToken = tokenInfo.get("refresh_token");
        String NaverTokenType = tokenInfo.get("token_type");
        int NaverExpiresIn = Integer.parseInt(tokenInfo.get("expires_in"));

        HttpEntity<MultiValueMap<String,String>> profileRequest = generateProfileRequest(NaverAccessToken);
        ResponseEntity<String> profileInfo = requestProfile(profileRequest);


        JSONParser parser = new JSONParser();
        JSONObject profileData = (JSONObject) parser.parse(profileInfo.getBody().toString());

        String responseData = profileData.get("response").toString();
        JSONObject response = (JSONObject) parser.parse(responseData);

        String name = response.get("name").toString();
        String naverId = response.get("id").toString();

        Member loginMember = naverMemberService.getLoginMember(naverId,name);


        Map<String,Object> loginData = new HashMap<>();
        Map<String,Object> accessToken =jwtTokenService.getTokens(loginMember);

        loginData.put("memberName",loginMember.getName());
        loginData.put("memberIdx",loginMember.getIdx());
        loginData.put("accessToken",accessToken.get("access-token"));
        loginData.put("refreshToken",accessToken.get("refresh-token"));

        jwtTokenService.saveRefreshToken(loginMember.getIdx(), accessToken.get("refresh-token").toString());
        accessToken.put("message","success");
        HttpStatus status = HttpStatus.ACCEPTED;

        return new ResponseEntity<Map<String,Object>>(loginData,status);
    }

    private Map<String,String> getTokenInfo(String json) throws ParseException {
        Map<String,String> tokens = new HashMap<>();

        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(json);
        JSONObject jsonObject = (JSONObject) obj;

        tokens.put("access_token",jsonObject.get("access_token").toString());
        tokens.put("refresh_token",jsonObject.get("refresh_token").toString());
        tokens.put("expires_in",jsonObject.get("expires_in").toString());
        tokens.put("token_type",jsonObject.get("token_type").toString());
        return tokens;
    }

    private HttpEntity<MultiValueMap<String, String>> generateAuthCodeRequest(String authCode, String state){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id",CLIENT_ID);
        params.add("client_secret",CLIENT_SECRET);
        params.add("code",authCode);
        params.add("state",state);
        return new HttpEntity<>(params,headers);
    }

    private ResponseEntity<String> requestAccessToken(HttpEntity request){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                request,
                String.class
        );
    }
    private HttpEntity<MultiValueMap<String,String>> generateProfileRequest(String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization","Bearer " +accessToken);
        return new HttpEntity<>(headers);
    }
    private ResponseEntity<String> requestProfile(HttpEntity request){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.GET,
                request,
                String.class
        );
    }
}


