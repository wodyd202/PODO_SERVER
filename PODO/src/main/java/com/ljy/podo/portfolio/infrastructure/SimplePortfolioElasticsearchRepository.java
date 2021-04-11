package com.ljy.podo.portfolio.infrastructure;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.springframework.stereotype.Repository;

import com.ljy.podo.portfolio.PortfolioId;
import com.ljy.podo.portfolio.aggregate.Portfolio;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@SuppressWarnings("deprecation")
public class SimplePortfolioElasticsearchRepository implements PortfolioElasticsearchRepository {

	private final RestHighLevelClient client;
	private final String PORTFOLIO = "podo";
	private final String TYPE_NAME = "_doc";

	@Override
	public void save(Portfolio entity) throws IOException {
		IndexRequest request_portfolio = new IndexRequest(PORTFOLIO, TYPE_NAME, entity.getId().toString());
		request_portfolio.source(XContentFactory.jsonBuilder().startObject()
				.field("title", entity.getTitle().toString()).field("header", entity.getHeader().toString())
				.field("content", entity.getDetail().getContent().toString())
				.field("major", entity.getMajor().toString()).field("writer", entity.getWriter().toString())
				.field("createDate", entity.getCreateDate().toString()).field("state", entity.getState().toString())
				.endObject());
		client.index(request_portfolio, RequestOptions.DEFAULT);
	}

	@Override
	public void update(Portfolio entity) throws IOException {
		UpdateRequest request = new UpdateRequest(PORTFOLIO, entity.getId().toString());
		String jsonString = "{"
				+ "\"title\":\""+entity.getTitle().toString()+"\","
				+ "\"content\":\""+entity.getDetail().getContent().toString()+"\","
				+ "\"header\":\""+entity.getHeader().toString()+"\","
				+ "\"showType\":\""+entity.getShowType()+"\","
				+ "\"state\":\""+entity.getState()+"\""
				+ "}";
		request.doc(jsonString,XContentType.JSON);
		client.update(request, RequestOptions.DEFAULT);
	}

	@Override
	public void delete(PortfolioId entity) throws IOException {
		UpdateRequest request = new UpdateRequest(PORTFOLIO, entity.toString());
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("state", "DELETE");
		Script inline = new Script(ScriptType.INLINE, "painless", "ctx._source.state = params.state", parameters);
		request.script(inline);
		client.update(request, RequestOptions.DEFAULT);
	}

}
