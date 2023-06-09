package com.ssafy.camping.member.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class  Member{
	@Nullable private int idx;
	@NonNull private String name;
	@NonNull private String id;
	@NonNull private String pwd;
}
