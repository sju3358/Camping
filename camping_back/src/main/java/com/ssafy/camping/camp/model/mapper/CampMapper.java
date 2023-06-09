package com.ssafy.camping.camp.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.camping.camp.dto.Gugun;
import com.ssafy.camping.camp.dto.Sido;
import com.ssafy.camping.camp.dto.Camp;

@Mapper
public interface CampMapper {
	public abstract List<Camp> selectAll(int now) throws SQLException;

	public abstract List<Sido> selectSidoCode() throws SQLException;

	public abstract List<Gugun> selectGugunCode(int sido_code) throws SQLException;

	public abstract List<Camp> selectBySidoGugun(int sido_code, int gugun_code) throws SQLException;
	
	public abstract Camp selectById(int content_id) throws SQLException;

	public abstract int countPage() throws SQLException;
}
