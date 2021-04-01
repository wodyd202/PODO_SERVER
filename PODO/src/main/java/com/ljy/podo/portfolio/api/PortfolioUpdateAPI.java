package com.ljy.podo.portfolio.api;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ljy.podo.config.security.oauth.LoginUser;
import com.ljy.podo.portfolio.service.deletePortoflio.DeletePortfolio;
import com.ljy.podo.portfolio.service.deletePortoflio.service.PortfolioDeleteService;
import com.ljy.podo.portfolio.service.registerPortfolio.RegisterPortfolio;
import com.ljy.podo.portfolio.service.registerPortfolio.service.PortfolioFileUploadService;
import com.ljy.podo.portfolio.service.registerPortfolio.service.PortfolioRegisterService;
import com.ljy.podo.portfolio.service.updatePortfolio.UpdatePortfolio;
import com.ljy.podo.portfolio.service.updatePortfolio.service.PortfolioUpdateService;
import com.ljy.podo.user.aggregate.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/portfolio")
@RequiredArgsConstructor
public class PortfolioUpdateAPI {
	
	private final PortfolioRegisterService portfolioRegisterService;
	private final PortfolioUpdateService portfolioUpdateService;
	private final PortfolioDeleteService portfolioDeleteService;
	private final PortfolioFileUploadService portfolioFileUploadService;
	
	@PostMapping
	public ResponseEntity<RegisterPortfolio> register(@RequestBody RegisterPortfolio registerPortfolio, @LoginUser User user){
		registerPortfolio.setMajorAndWriter(user);
		portfolioRegisterService.register(registerPortfolio);
		return new ResponseEntity<>(registerPortfolio,HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<UpdatePortfolio> update(@RequestBody UpdatePortfolio updatePortfolio, @LoginUser User user){
		updatePortfolio.setUpdater(user);
		portfolioUpdateService.register(updatePortfolio);
		return new ResponseEntity<>(updatePortfolio,HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity<DeletePortfolio> delete(@RequestBody DeletePortfolio deletePortfolio,@LoginUser User user){
		deletePortfolio.setDeleter(user);
		portfolioDeleteService.delete(deletePortfolio);
		return new ResponseEntity<>(deletePortfolio,HttpStatus.OK);
	}
	
	@PostMapping("fileUpload")
	public String fileUpload(MultipartFile file) throws IOException {
		return portfolioFileUploadService.uploadFile(file);
	}
}
