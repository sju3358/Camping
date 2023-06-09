package com.ssafy.camping.board.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.camping.board.dto.Board;
import com.ssafy.camping.board.model.service.BoardService;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;

@RequestMapping("/board")
@RestController
public class BoardController {

	private final BoardService boardService;

	@Autowired
	public BoardController(BoardService boardService) {this.boardService = boardService;}
	
	
	@GetMapping("/{now}")
	public ResponseEntity<?> getBoardList(@PathVariable int now) throws SQLException {

		Map<String,Object> data = new HashMap<>();
		List<Board> boardList = boardService.getAllBoard(now);
		data.put("boardList",boardList);
		data.put("message","success");
		HttpStatus stats = HttpStatus.ACCEPTED;
		return new ResponseEntity<>(data,stats);
	}

	@GetMapping("/search/{title}/{now}")
	public ResponseEntity<?> getBoardListByTitle(@PathVariable String title, @PathVariable int now) throws SQLException{
		List<Board> boardList = boardService.getBoardByTitle(title, now);
		return ResponseEntity.ok().body(boardList);
	}

	@GetMapping("/content/{idx}")
	public ResponseEntity<?> getBoardDetail(@PathVariable int idx) throws SQLException {
		Board board = boardService.getBoardByIdx(idx);
		return ResponseEntity.ok().body(board);
	}
	
	@PostMapping
	public ResponseEntity<?> registBoard(@RequestBody Board board) throws SQLException{
		boardService.addBoard(board);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{idx}")
	public ResponseEntity<?> deleteBoard(@PathVariable int idx) throws SQLException{
		boardService.deleteByIdx(idx);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<?> countPage() throws SQLException{
		return ResponseEntity.ok().body(boardService.countPage());
	}

	@GetMapping("/search/{title}")
	public ResponseEntity<?> countSearchPage(@PathVariable String title) throws SQLException{
		return ResponseEntity.ok().body(boardService.countSearchPage(title));
	}
}
