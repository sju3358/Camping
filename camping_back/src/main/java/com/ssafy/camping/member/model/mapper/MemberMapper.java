package com.ssafy.camping.member.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.camping.member.dto.Member;

@Mapper
public interface MemberMapper {
	public abstract Member findUserById(String targetId) throws SQLException;
	public abstract Member findUserByIdx(int targetIdx) throws SQLException;
	public abstract int findUserIdxById(String targetId) throws SQLException;
	public abstract void insertUser(Member member) throws SQLException;
	public abstract void updateUserPasswordByIdx(Map<String, String> param) throws SQLException;
	public abstract void deleteUserByIdx(int targetIdx) throws SQLException;
}
