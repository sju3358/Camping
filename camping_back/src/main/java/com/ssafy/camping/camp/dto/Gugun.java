package com.ssafy.camping.camp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Gugun {
    @NonNull private int gugun_code;
    @NonNull private String gugun_name;
    @NonNull private int sido_code;
}
