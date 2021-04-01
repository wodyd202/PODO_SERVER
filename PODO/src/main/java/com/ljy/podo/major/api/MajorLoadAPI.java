package com.ljy.podo.major.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ljy.podo.major.infrastructure.SimpleMajorRepository;
import com.ljy.podo.major.projection.MajorListData;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/major")
@RequiredArgsConstructor
public class MajorLoadAPI {
	private final SimpleMajorRepository majorRepository;
	
	@GetMapping
	public ResponseEntity<List<MajorListData>> findAll(){
		return new ResponseEntity<>(majorRepository.findAll(),HttpStatus.OK);
	}
}
