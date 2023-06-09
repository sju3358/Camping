package com.ssafy.camping.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Review {
    int idx;
    @NonNull int content_id;
    @NonNull int user_idx;
    @NonNull String comment;
    String write_date;
}
