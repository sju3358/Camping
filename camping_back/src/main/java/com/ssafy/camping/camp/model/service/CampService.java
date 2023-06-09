package com.ssafy.camping.camp.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.camping.camp.dto.Gugun;
import com.ssafy.camping.camp.dto.Sido;
import com.ssafy.camping.camp.dto.Camp;
import com.ssafy.camping.camp.model.mapper.CampMapper;

@Service
public class CampService {
	private final CampMapper campMapper;

	@Autowired
	public CampService(CampMapper campMapper) {this.campMapper = campMapper;}
	
	public List<Camp> getAllCamp(int now) throws SQLException {
		return campMapper.selectAll(now);
	}

	public List<Sido> getSidoCode() throws SQLException{
		return campMapper.selectSidoCode();
	}

	public List<Gugun> getGugunCode(int sido_code) throws SQLException{
		return campMapper.selectGugunCode(sido_code);
	}
	
	public List<Camp> getCampBySidoGugun(int sido_code, int gugun_code) throws SQLException {
		return campMapper.selectBySidoGugun(sido_code, gugun_code);
	}
	
	public Camp getCampById(int content_id) throws SQLException {
		return campMapper.selectById(content_id);
	}

	public int countPage() throws SQLException{
		return campMapper.countPage();
	}
}
