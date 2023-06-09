package com.ssafy.camping.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Board {
	int idx;
	@NonNull int user_idx;
	@NonNull String title;
	@NonNull String text;
	String write_date;
	int count_review;
}
