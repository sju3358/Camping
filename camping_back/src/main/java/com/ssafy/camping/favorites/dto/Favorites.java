package com.ssafy.camping.favorites.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Favorites {
    int idx;
    @NonNull int content_id;
    @NonNull int user_idx;
}
