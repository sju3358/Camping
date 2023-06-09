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
public class NaverMemberService {
    private final MemberMapper memberMapper;
    private final PasswordUtil passwordUtil;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public NaverMemberService(
            MemberMapper memberMapper,
            PasswordUtil passwordUtil,
            JwtTokenService jwtTokenService){

        this.memberMapper = memberMapper;
        this.passwordUtil = passwordUtil;
        this.jwtTokenService = jwtTokenService;
    }

    public Member getLoginMember(String memberId, String name) throws SQLException, NoSuchAlgorithmException, NullPointerException {

        Member loginUser = memberMapper.findUserById(memberId);

        if(loginUser == null) {
            this.register(memberId, name);
            loginUser = memberMapper.findUserById(memberId);
        }
        return loginUser;
    }

    public void register(String memberId, String memberName) throws SQLException, NoSuchAlgorithmException {

        String memberPassword = passwordUtil.getRandomPassword(12);
        String memberPasswordEncoded = passwordUtil.encodePassword(memberPassword);
        memberMapper.insertUser(new Member(memberName,memberId,memberPasswordEncoded));
    }

}
