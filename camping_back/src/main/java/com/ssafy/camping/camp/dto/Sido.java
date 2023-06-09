package com.ssafy.camping.camp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Sido {
    @NonNull private int sido_code;
    @NonNull private String sido_name;
}
