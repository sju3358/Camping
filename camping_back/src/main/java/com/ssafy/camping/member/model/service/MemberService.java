package com.ssafy.camping.member.model.service;

import com.ssafy.camping.member.dto.Member;
import com.ssafy.camping.member.exception.DuplicatedMemberIdException;
import com.ssafy.camping.member.exception.InvalidMemberPasswordException;
import com.ssafy.camping.member.exception.NullValueException;
import com.ssafy.camping.member.exception.RegexException;
import com.ssafy.camping.member.model.mapper.MemberMapper;
import com.ssafy.camping.member.util.PasswordUtil;
import com.ssafy.camping.member.util.RegexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MemberService {

    private final RegexUtil regexUtil;
    private final MemberMapper memberMapper;
    private final PasswordUtil passwordUtil;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public MemberService(
            RegexUtil regexUtil,
            MemberMapper memberMapper,
            PasswordUtil passwordUtil,
            JwtTokenService jwtTokenService){

        this.memberMapper = memberMapper;
        this.passwordUtil = passwordUtil;
        this.jwtTokenService = jwtTokenService;
        this.regexUtil = regexUtil;
    }

    public Member getLoginMember(Member loginInfo) throws SQLException, NoSuchAlgorithmException, NullPointerException {

        if(loginInfo.getId() == "" || loginInfo.getPwd() == "")
            throw new NullValueException("비어있는 칸이 존재합니다.");

        Member loginUser = memberMapper.findUserById(loginInfo.getId());

        if(loginUser == null)
            throw new NullValueException("존재하지 않는 회원입니다.");

        if(jwtTokenService.getRefreshToken(loginUser.getIdx()) != null)
            throw new DuplicatedMemberIdException("이미 로그인 중입니다.");

        if(passwordUtil.match(loginInfo.getPwd(), loginUser.getPwd()) != true)
            throw new InvalidMemberPasswordException("비밀번호가 일치하지 않습니다.");

        return loginUser;
    }

    public void register(Map<String,String> registerMemberInfo) throws SQLException, NoSuchAlgorithmException {


        if( registerMemberInfo.get("registerId") == "" ||
            registerMemberInfo.get("registerPassword") == "" ||
            registerMemberInfo.get("registerPasswordRepeat") == "" ||
            registerMemberInfo.get("registerName") == "" )
            throw new NullValueException("비어있는 칸이 존재합니다");

        if(memberMapper.findUserById(registerMemberInfo.get("registerId")) != null)
            throw new DuplicatedMemberIdException();



        String memberId = registerMemberInfo.get("registerId");
        String memberPassword = registerMemberInfo.get("registerPassword");
        String memberPasswordRepeat = registerMemberInfo.get("registerPasswordRepeat");
        String memberName = registerMemberInfo.get("registerName");

        if(regexUtil.checkValidationRegisterId(memberId) != true)
            throw new RegexException("아이디 형식이 맞지 않습니다.");

        if(regexUtil.checkValidationRegisterPassword(memberPassword) != true)
            throw new RegexException("비밀번호 형식이 맞지 않습니다.");

        if(memberPassword.equals(memberPasswordRepeat) != true)
            throw new InvalidMemberPasswordException("비밀번호 일치하지 않습니다");

        String memberPasswordEncoded = passwordUtil.encodePassword(memberPassword);
        memberMapper.insertUser(new Member(memberName,memberId,memberPasswordEncoded));
    }

    public void deactivate(int targetIdx) throws SQLException{
        memberMapper.deleteUserByIdx(targetIdx);
    }

    public void changePassword(int memberIdx, String curPassword, String newPassword, String newPasswordRepeat) throws SQLException, NoSuchAlgorithmException {

        Member member = memberMapper.findUserByIdx(memberIdx);

        if(passwordUtil.match(curPassword,member.getPwd()) != true)
            throw new InvalidMemberPasswordException("현재 비밀번호가 일치하지 않습니다.");

        if(regexUtil.checkValidationRegisterPassword(newPassword) != true)
            throw new RegexException("비밀번호 형식이 맞지 않습니다.");

        if(newPassword.equals(newPasswordRepeat) != true)
            throw new InvalidMemberPasswordException("변경할 비밀번호가 일치 하지 않습니다.");


        String newPasswordEncoded = passwordUtil.encodePassword(newPassword);

        Map<String, String> param = new HashMap<>();
        param.put("idx",Integer.toString(memberIdx));
        param.put("pwd",newPasswordEncoded);

        memberMapper.updateUserPasswordByIdx(param);
    }

    public void changePassword(String memberId, String newPassword) throws SQLException, NoSuchAlgorithmException {


       int memberIdx = memberMapper.findUserIdxById(memberId);

        String newPasswordEncoded = passwordUtil.encodePassword(newPassword);

        Map<String, String> param = new HashMap<>();
        param.put("idx",Integer.toString(memberIdx));
        param.put("pwd",newPasswordEncoded);

        memberMapper.updateUserPasswordByIdx(param);
    }

}
