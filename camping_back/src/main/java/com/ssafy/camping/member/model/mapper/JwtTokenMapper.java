package com.ssafy.camping.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.Map;

@Mapper
public interface JwtTokenMapper {
    public abstract String findRefreshTokenByUserIdx(int param) throws SQLException;
    public abstract void insertRefreshTokenByUserIdx(Map<String, String> params) throws SQLException;
    public abstract void updateRefreshTokenByUserIdx(Map<String, String> params) throws SQLException;
    public abstract void deleteRefreshTokenByUserIdx(int params) throws SQLException;
}
