package com.ssafy.camping.camp.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.camping.camp.dto.Gugun;
import com.ssafy.camping.camp.dto.Sido;
import com.ssafy.camping.camp.dto.Camp;
import com.ssafy.camping.camp.model.service.CampService;

@RequestMapping("/camp")
@RestController
@CrossOrigin("*")
public class CampController {

	private final CampService campsiteService;

	@Autowired
	public CampController(CampService campsiteService) {this.campsiteService = campsiteService;}
	
	@GetMapping("/{now}")
	public ResponseEntity<?> getCampList(@PathVariable int now) throws SQLException{
		List<Camp> campList = campsiteService.getAllCamp(now);
		
		return ResponseEntity.ok().body(campList);
	}

	@GetMapping("/location")
	public ResponseEntity<?> getSidoCode() throws SQLException{
		List<Sido> sidoList = campsiteService.getSidoCode();
		return ResponseEntity.ok().body(sidoList);
	}
    
	@GetMapping("/location/{sido_code}")
	public ResponseEntity<?> getGugunCode(@PathVariable int sido_code) throws SQLException{
		List<Gugun> guGunList = campsiteService.getGugunCode(sido_code);
		return ResponseEntity.ok().body(guGunList);
	}

	@GetMapping("/location/{sido_code}/{gugun_code}")
	public ResponseEntity<?> getCampListBySidoGugun(@PathVariable int sido_code, @PathVariable int gugun_code) throws SQLException{
		List<Camp> campList = campsiteService.getCampBySidoGugun(sido_code, gugun_code);
		return ResponseEntity.ok().body(campList);
	}

	@GetMapping("/content/{content_id}")
	public ResponseEntity<?> getCampDetail(@PathVariable int content_id) throws SQLException{
		Camp camp = campsiteService.getCampById(content_id);
		return ResponseEntity.ok().body(camp);
	}

	@GetMapping
	public ResponseEntity<?> countPage() throws SQLException{
		return ResponseEntity.ok().body(campsiteService.countPage());
	}
}
