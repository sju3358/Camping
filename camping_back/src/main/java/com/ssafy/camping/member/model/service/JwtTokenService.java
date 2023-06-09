package com.ssafy.camping.member.model.service;

import com.ssafy.camping.member.dto.Member;
import com.ssafy.camping.member.model.mapper.JwtTokenMapper;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import javax.xml.datatype.DatatypeConstants;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtTokenService {
    private final JwtTokenMapper jwtTokenMapper;
    private static final String SALT = "campingSecretcampingSecretcampingSecretcampingSecret";
    private static final int ACCESS_TOKEN_EXPIRE_MINUTES = 1; // 분단위
    private static final int REFRESH_TOKEN_EXPIRE_MINUTES = 2; // 주단위

    @Autowired
    public JwtTokenService(JwtTokenMapper jwtTokenMapper){
        this.jwtTokenMapper = jwtTokenMapper;
    }

    public <T> String createAccessToken(String key, T data) throws UnsupportedEncodingException {
        //front-end에서 refresh token에 대한 기능 구현 미완으로 인해 임시로 만료기한을 길게 잡습니다.
        return create(key, data, "access-token", 1000 * 60 * 60 * 24 * 7 * REFRESH_TOKEN_EXPIRE_MINUTES);
    }
    public <T> String createRefreshToken(String key, T data) throws UnsupportedEncodingException {
        return create(key, data, "refresh-token", 1000 * 60 * 60 * 24 * 7 * REFRESH_TOKEN_EXPIRE_MINUTES);
    }

    private <T> String create(String key, T data, String subject, long expire) throws UnsupportedEncodingException {

        Claims claims = Jwts.claims()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expire));

        claims.put(key, data);

        SignatureAlgorithm signAlgorithm = SignatureAlgorithm.HS256;

        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .signWith(signAlgorithm, SALT.getBytes("UTF-8"))
                .compact();

        return jwt;
    }

    public Map<String,Object> getTokens(Member loginInfo) throws SQLException, UnsupportedEncodingException {

        Map<String, Object> tokens = new HashMap<>();

        String accessToken = this.createAccessToken("memberId", loginInfo.getId());
        String refreshToken = this.createRefreshToken("memberId", loginInfo.getId());

        tokens.put("access-token", accessToken);
        tokens.put("refresh-token", refreshToken);

        return tokens;
    }

    public void saveRefreshToken(int userIdx, String refreshToken) throws SQLException {
        Map<String, String> map = new HashMap<>();
        map.put("userIdx",Integer.toString(userIdx));
        map.put("token",refreshToken);
        jwtTokenMapper.insertRefreshTokenByUserIdx(map);
    }

    public String getRefreshToken(int userIdx) throws SQLException {
        return jwtTokenMapper.findRefreshTokenByUserIdx(userIdx);
    }

    public void updateRefreshToken(int userIdx, String refreshToken) throws SQLException {
        Map<String, String> map = new HashMap<>();
        map.put("userid",Integer.toString(userIdx));
        map.put("token",refreshToken);
        jwtTokenMapper.updateRefreshTokenByUserIdx(map);
    }

    public void deleteRefreshToken(int userIdx) throws SQLException {
        jwtTokenMapper.deleteRefreshTokenByUserIdx(userIdx);
    }

    public boolean checkToken(String jwt) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SALT.getBytes("UTF-8")).parseClaimsJws(jwt);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

