package com.ssafy.camping.camp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Camp {
	@NonNull private int content_id;
	@NonNull private String title;
	@NonNull private String addr;
	@NonNull private int sido_code;
	@NonNull private int gugun_code;
	@NonNull private double latitude;
	@NonNull private double longitude;
	@NonNull private String img;
	@NonNull private String overview;
	int count_favorites;
}
