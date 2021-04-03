package com.ljy.podo.runner;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.ljy.podo.major.infrastructure.SimpleMajorRepository;
import com.ljy.podo.user.Major;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AppRunner implements ApplicationRunner{
	
	private final SimpleMajorRepository majorRepository;
	
	@Value("${major.list}")
	private List<String> majors;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
//		majors.forEach(c->{
//			majorRepository.save(new Major(c));
//		});
	}

}
