package com.ljy.podo.portfolio.infrastructure;

import java.io.IOException;
import java.util.List;

import com.ljy.podo.portfolio.PortfolioId;
import com.ljy.podo.portfolio.aggregate.Portfolio;
import com.ljy.podo.portfolio.service.loadPortfolio.PortfolioSearchDTO;
import com.ljy.podo.portfolio.service.loadPortfolio.projection.PortfolioListData;

public interface PortfolioElasticsearchRepository {
	List<PortfolioListData> findAll(PortfolioSearchDTO searchDTO) throws IOException;
	List<PortfolioListData> recomand(PortfolioSearchDTO searchDTO) throws IOException;
	void save(Portfolio entity) throws IOException;
	void saveSearchKeyword(PortfolioSearchDTO dto) throws IOException;
	void update(Portfolio entity) throws IOException;
	void delete(PortfolioId entity) throws IOException;
}
