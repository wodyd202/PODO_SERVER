package com.ljy.podo.portfolio.infrastructure;

import java.io.IOException;

import com.ljy.podo.portfolio.PortfolioId;
import com.ljy.podo.portfolio.aggregate.Portfolio;

public interface PortfolioElasticsearchRepository {
	void save(Portfolio entity) throws IOException;
	void update(Portfolio entity) throws IOException;
	void delete(PortfolioId entity) throws IOException;
}
