package com.ssafy.camping.board.model.service;

import com.ssafy.camping.board.dto.Board;
import com.ssafy.camping.board.model.mapper.BoardMapper;
import java.sql.SQLException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

	private final BoardMapper boardMapper;

	@Autowired
	public BoardService(BoardMapper boardMapper) {this.boardMapper = boardMapper;}
	
	public List<Board> getAllBoard(int now) throws SQLException{
		return boardMapper.selectAll(now);
	}
	public List<Board> getBoardByTitle(String title, int now) throws SQLException {
		return boardMapper.selectBoardByTitle(title, now);
	}
	public Board getBoardByIdx(int idx) throws SQLException{
		return boardMapper.selectBoardByIdx(idx);
	}
	public void addBoard(Board board) throws SQLException {
		boardMapper.insertBoard(board);
	}
	public void deleteByIdx(int idx) throws SQLException{
		boardMapper.deleteByIdx(idx);
	}

	public int countPage() throws SQLException{
		return boardMapper.countPage();
	}

	public int countSearchPage(String title) throws SQLException{
		return boardMapper.countSearchPage(title);
	}
}
