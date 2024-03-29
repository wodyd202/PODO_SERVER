package com.ljy.podo.portfolio.api;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ljy.podo.config.security.oauth.LoginUser;
import com.ljy.podo.portfolio.PortfolioId;
import com.ljy.podo.portfolio.PortfolioState;
import com.ljy.podo.portfolio.aggregate.exception.InvalidPortfolioException;
import com.ljy.podo.portfolio.aggregate.exception.PortfolioNotFindException;
import com.ljy.podo.portfolio.infrastructure.SimplePortfolioElasticsearchRepository;
import com.ljy.podo.portfolio.service.loadPortfolio.PortfolioSearchDTO;
import com.ljy.podo.portfolio.service.loadPortfolio.projection.PortfolioFullData;
import com.ljy.podo.portfolio.service.loadPortfolio.projection.PortfolioList;
import com.ljy.podo.portfolio.service.loadPortfolio.projection.PortfolioListData;
import com.ljy.podo.portfolio.service.loadPortfolio.service.PortfolioLoadService;
import com.ljy.podo.user.aggregate.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/portfolio")
@RequiredArgsConstructor
public class PortfolioLoadAPI {
	private final PortfolioLoadService portfolioRepository;
	private final SimplePortfolioElasticsearchRepository elasticsearchRepository;
	
	@GetMapping("recommand")
	public ResponseEntity<List<PortfolioListData>> recommand(PortfolioSearchDTO searchDTO, @LoginUser User user) throws IOException{
		searchDTO.setMajor(user);
		searchDTO.setUser(user);
		List<PortfolioListData> findAll = elasticsearchRepository.recomand(searchDTO);
		elasticsearchRepository.saveSearchKeyword(searchDTO);
		return new ResponseEntity<>(findAll,HttpStatus.OK);
	}
	
	@GetMapping("search")
	public ResponseEntity<List<PortfolioListData>> search(PortfolioSearchDTO searchDTO, @LoginUser User user) throws IOException{
		searchDTO.setMajor(user);
		List<PortfolioListData> findAll = elasticsearchRepository.findAll(searchDTO);
		elasticsearchRepository.saveSearchKeyword(searchDTO);
		return new ResponseEntity<>(findAll,HttpStatus.OK);
	}
	
	@GetMapping("count")
	public ResponseEntity<Long> countAll(){
		PortfolioSearchDTO searchDTO = PortfolioSearchDTO.builder()
				.state(PortfolioState.CREATE)
				.build();
		return new ResponseEntity<>(portfolioRepository.countAll(searchDTO),HttpStatus.OK);
	}
	
	@GetMapping("equal-major")
	public ResponseEntity<PortfolioList> findByMajor(PortfolioSearchDTO searchDTO, @LoginUser User user){
		searchDTO.setMajor(user);
		PortfolioList portfolioList = new PortfolioList(portfolioRepository.findAll(searchDTO),
				portfolioRepository.countAll(searchDTO));
		return new ResponseEntity<>(portfolioList, HttpStatus.OK);
	}
	
	@GetMapping("all")
	public ResponseEntity<PortfolioList> findAll(PortfolioSearchDTO searchDTO, @LoginUser User user) {
		if (searchDTO.getEmail() == null) {
			throw new InvalidPortfolioException("글 작성자를 입력해주세요.", "email");
		}
		PortfolioList portfolioList = new PortfolioList(portfolioRepository.findAll(searchDTO),
				portfolioRepository.countAll(searchDTO));
		return new ResponseEntity<>(portfolioList, HttpStatus.OK);
	}

	@GetMapping("is-temporary")
	public ResponseEntity<List<PortfolioListData>> findAllbyIsTemporary(PortfolioSearchDTO searchDTO,
			@LoginUser User user) {
		searchDTO.setTemporary(user);
		return new ResponseEntity<>(portfolioRepository.findAll(searchDTO), HttpStatus.OK);
	}

	@GetMapping("is-today")
	public ResponseEntity<PortfolioList> findAllbyIsToday(PortfolioSearchDTO searchDTO) {
		searchDTO.setToday();
		PortfolioList portfolioList = new PortfolioList(portfolioRepository.findAll(searchDTO),
				portfolioRepository.countAll(searchDTO));
		return new ResponseEntity<>(portfolioList, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<PortfolioFullData> findById(@RequestParam(defaultValue = "") String id) {
		if (id.equals("")) {
			throw new InvalidPortfolioException("포트폴리오 고유 번호를 입력해주세요.", "portfolio");
		}
		PortfolioFullData portfolio = portfolioRepository.findPortfolioViewDataById(new PortfolioId(id))
				.orElseThrow(() -> new PortfolioNotFindException("포트폴리오 정보가 존재하지 않습니다.", "portfolio"));
		return new ResponseEntity<>(portfolio, HttpStatus.OK);
	}
}
