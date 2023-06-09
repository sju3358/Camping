package com.ssafy.camping.board.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.camping.board.dto.Board;

@Mapper
public interface BoardMapper {

	public abstract List<Board> selectAll(int now) throws SQLException;

	public abstract List<Board> selectBoardByTitle(String title, int now) throws SQLException;

	public abstract Board selectBoardByIdx(int idx) throws SQLException;

	public abstract void insertBoard(Board board) throws SQLException;
	
	public abstract void deleteByIdx(int idx) throws SQLException;

	public abstract int countPage() throws SQLException;

	public abstract int countSearchPage(String title) throws SQLException;
}
