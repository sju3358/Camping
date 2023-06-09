package com.ssafy.camping.member.controller;


import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;


import com.ssafy.camping.member.model.service.JwtTokenService;
import com.ssafy.camping.member.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.camping.member.dto.Member;



@RequestMapping("/auth")
@RestController
public class AuthorizationController{

	private final MemberService memberService;
	private final JwtTokenService jwtTokenService;

	@Autowired
	public AuthorizationController(JwtTokenService jwtTokenService, MemberService memberService) {
		this.jwtTokenService = jwtTokenService;
		this.memberService = memberService;
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String,Object>> loginRequest(@RequestBody Map<String,String> param) throws SQLException, UnsupportedEncodingException, NoSuchAlgorithmException {

		String loginId = param.get("id");
		String loinPwd = param.get("pwd");

		Member loginMember = memberService.getLoginMember(new Member("",loginId,loinPwd));

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

	@PostMapping("/register")
	public ResponseEntity<Map<String,Object>> registerRequest(@RequestBody Map<String,String> param) throws SQLException, UnsupportedEncodingException, NoSuchAlgorithmException {

		String registerId = param.get("registerId");
		String registerPassword = param.get("registerPassword");
		String registerPasswordRepeat = param.get("registerPasswordRepeat");
		String registerName = param.get("registerName");

		Map<String, String> registerInfo = new HashMap<>();
		registerInfo.put("registerId",registerId);
		registerInfo.put("registerPassword",registerPassword);
		registerInfo.put("registerPasswordRepeat",registerPasswordRepeat);
		registerInfo.put("registerName",registerName);

		memberService.register(registerInfo);

		Map<String,Object> token = new HashMap<>();
		token.put("message","success");
		HttpStatus status = HttpStatus.ACCEPTED;

		return new ResponseEntity<Map<String,Object>>(token,status);
	}



	@DeleteMapping("/logout/{targetIdx}")
	ResponseEntity<Map<String,Object>> logoutRequest(@PathVariable int targetIdx) throws SQLException {
		jwtTokenService.deleteRefreshToken(targetIdx);
		Map<String,Object> token = new HashMap<>();
		token.put("message","success");
		HttpStatus status = HttpStatus.ACCEPTED;

		return new ResponseEntity<Map<String,Object>>(token,status);
	}


}
