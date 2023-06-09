package com.ssafy.camping.member.controller;


import com.ssafy.camping.member.model.service.JwtTokenService;
import com.ssafy.camping.member.model.service.MailService;
import com.ssafy.camping.member.model.service.MemberService;
import com.ssafy.camping.member.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/info")
@RestController
public class InfoController {
    private final MemberService memberService;
    private final PasswordUtil passwordUtil;
    private final MailService mailService;
    private final JwtTokenService jwtTokenService;
    @Autowired
    public InfoController(MailService mailService,PasswordUtil passwordUtil,JwtTokenService jwtTokenService, MemberService memberService) {
        this.mailService = mailService;
        this.passwordUtil = passwordUtil;
        this.jwtTokenService = jwtTokenService;
        this.memberService = memberService;
    }

    @DeleteMapping("/deactivate/{targetIdx}")
    public ResponseEntity<Map<String,Object>> deactivateRequest(@PathVariable int targetIdx) throws SQLException {
        jwtTokenService.deleteRefreshToken(targetIdx);
        memberService.deactivate(targetIdx);
        Map<String,Object> token = new HashMap<>();
        token.put("message","success");
        HttpStatus status = HttpStatus.ACCEPTED;

        return new ResponseEntity<Map<String,Object>>(token,status);
    }

    @PutMapping("/change-password")
    public ResponseEntity<Map<String,Object>> changePasswordRequest(@RequestBody Map<String,String> param) throws SQLException, NoSuchAlgorithmException {

        int memberIdx = Integer.parseInt(param.get("memberIdx"));
        String currentPassword = param.get("curPassword");
        String changePassword = param.get("newPassword");
        String changePasswordRepeat = param.get("newPasswordRepeat");

        memberService.changePassword(memberIdx,currentPassword,changePassword,changePasswordRepeat);

        jwtTokenService.deleteRefreshToken(memberIdx);
        Map<String,Object> token = new HashMap<>();
        token.put("message","success");
        HttpStatus status = HttpStatus.ACCEPTED;

        return new ResponseEntity<Map<String,Object>>(token,status);
    }

        @PostMapping("/find-password")
        public ResponseEntity<Map<String,Object>> findPasswordRequest(@RequestBody Map<String,String> param) throws Exception {
            String memberId = param.get("memberId");
            String newPassword = passwordUtil.getRandomPassword(12);
            String toEmail = param.get("toEmail");


            memberService.changePassword(memberId,newPassword);

            mailService.sendTempPasswordEmail(newPassword,toEmail);

            Map<String,Object> token = new HashMap<>();
            token.put("message","success");
            HttpStatus status = HttpStatus.ACCEPTED;

            return new ResponseEntity<Map<String,Object>>(token,status);
        }
}
