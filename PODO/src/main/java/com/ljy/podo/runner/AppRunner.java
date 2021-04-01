package com.ljy.podo.runner;

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
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
//		majorRepository.save(new Major("컴퓨터"));
//		majorRepository.save(new Major("미술"));
//		majorRepository.save(new Major("음악"));
//		majorRepository.save(new Major("체육"));
	}

}
