package com.ljy.podo.portfolio.infrastructure;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Repository;

import com.ljy.podo.portfolio.PortfolioId;
import com.ljy.podo.portfolio.PortfolioState;
import com.ljy.podo.portfolio.ShowType;
import com.ljy.podo.portfolio.aggregate.Portfolio;
import com.ljy.podo.portfolio.service.loadPortfolio.PortfolioSearchDTO;
import com.ljy.podo.portfolio.service.loadPortfolio.projection.PortfolioListData;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@SuppressWarnings("deprecation")
public class SimplePortfolioElasticsearchRepository implements PortfolioElasticsearchRepository {

	private final RestHighLevelClient client;
	private final String PODO = "podo";
	private final String PODO_SEARCH = "podo-search";
	private final String TYPE_NAME = "_doc";


	@Override
	public List<PortfolioListData> recomand(PortfolioSearchDTO searchDTO) throws IOException {
		List<PortfolioListData> result = new ArrayList<>();

		// 가장 많이 검색한 키워드 top 3
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("writer", searchDTO.getEmail()));
		TermsAggregationBuilder aggregation_keyword =  AggregationBuilders.terms("keywords").field("keyword").size(3);
		TermsAggregationBuilder aggregation_title =  AggregationBuilders.terms("titles").field("title").size(3);
		TermsAggregationBuilder aggregation_content =  AggregationBuilders.terms("contents").field("content").size(3);
		TermsAggregationBuilder aggregation_header =  AggregationBuilders.terms("headers").field("header").size(3);
		
		Collection<? extends Bucket> searchKeywords = getAggregationDatas(PODO_SEARCH,"keywords",boolQueryBuilder, aggregation_keyword);
		Collection<? extends Bucket> searchTitle = getAggregationDatas(PODO,"titles",boolQueryBuilder, aggregation_title);
		Collection<? extends Bucket> searchContent = getAggregationDatas(PODO,"contents",boolQueryBuilder, aggregation_content);
		Collection<? extends Bucket> searchHeader = getAggregationDatas(PODO,"headers",boolQueryBuilder, aggregation_header);
		
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("state", PortfolioState.CREATE.toString()));
		boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("major", searchDTO.getMajor()));
		boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("showType", ShowType.PUBLIC.toString()));
		boolQueryBuilder.mustNot(QueryBuilders.matchPhraseQuery("writer", searchDTO.getEmail().toString()));
		
		Iterator<? extends Bucket> searchKeywordsIterator = searchKeywords.iterator();
		Iterator<? extends Bucket> titlesIterator = searchTitle.iterator();
		Iterator<? extends Bucket> headersIterator = searchHeader.iterator();
		Iterator<? extends Bucket> contentsIterator = searchContent.iterator();

		String searchKeywordsStrBuilder = "";
		while(searchKeywordsIterator.hasNext()) {
			Bucket next = searchKeywordsIterator.next();
			searchKeywordsStrBuilder += next + " ";
		}
		boolQueryBuilder.should(QueryBuilders.matchQuery("title", searchKeywordsStrBuilder));
		boolQueryBuilder.should(QueryBuilders.matchQuery("content", searchKeywordsStrBuilder));
		boolQueryBuilder.should(QueryBuilders.matchQuery("header", searchKeywordsStrBuilder));
		
		String titleStrBuilder = "";
		while(titlesIterator.hasNext()) {
			Bucket next = titlesIterator.next();
			titleStrBuilder += next + " ";
		}
		boolQueryBuilder.must(QueryBuilders.matchQuery("content", titleStrBuilder));
		boolQueryBuilder.should(QueryBuilders.matchQuery("title", titleStrBuilder));
		boolQueryBuilder.should(QueryBuilders.matchQuery("header", titleStrBuilder));
		
		String headerStrBuilder = "";
		while(headersIterator.hasNext()) {
			Bucket next = headersIterator.next();
			headerStrBuilder += next + " ";
		}
		boolQueryBuilder.should(QueryBuilders.matchQuery("content", headerStrBuilder));
		boolQueryBuilder.should(QueryBuilders.matchQuery("title", headerStrBuilder));
		boolQueryBuilder.should(QueryBuilders.matchQuery("header", headerStrBuilder));

		String contentStrBuilder = "";
		while(contentsIterator.hasNext()) {
			Bucket next = contentsIterator.next();
			contentStrBuilder += next + " ";
		}
		boolQueryBuilder.should(QueryBuilders.matchQuery("content", contentStrBuilder));
		boolQueryBuilder.should(QueryBuilders.matchQuery("title", contentStrBuilder));
		boolQueryBuilder.should(QueryBuilders.matchQuery("header", contentStrBuilder));
		
		searchSourceBuilder.query(boolQueryBuilder);
		searchSourceBuilder.from(searchDTO.getPage() * searchDTO.getSize());
		searchSourceBuilder.size(searchDTO.getSize());
		SearchRequest request = new SearchRequest(PODO);
		request.source(searchSourceBuilder);

		SearchResponse response = client.search(request, RequestOptions.DEFAULT);
		response.getHits().forEach(c -> {
			Map<String, Object> sourceAsMap = c.getSourceAsMap();
			PortfolioListData portfolio;
			portfolio = PortfolioListData
					.builder()
					.id(c.getId())
					.title(sourceAsMap.get("title").toString())
					.writer(sourceAsMap.get("writer").toString())
					.header(sourceAsMap.get("header").toString())
					.content(sourceAsMap.get("content").toString())
					.build();
			result.add(portfolio);
		});
		
		return result;
	}

	@Override
	public List<PortfolioListData> findAll(PortfolioSearchDTO searchDTO) throws IOException {
		List<PortfolioListData> result = new ArrayList<>();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("state", PortfolioState.CREATE.toString()));
		boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("showType", ShowType.PUBLIC.toString()));
		boolQueryBuilder.must(QueryBuilders.matchQuery("title", searchDTO.getKeyword()));
		boolQueryBuilder.should(QueryBuilders.matchQuery("header", searchDTO.getKeyword()));
		boolQueryBuilder.should(QueryBuilders.matchQuery("content", searchDTO.getKeyword()));
		searchSourceBuilder.query(boolQueryBuilder);
		searchSourceBuilder.from(0);
		searchSourceBuilder.size(10);
		SearchRequest request = new SearchRequest(PODO);
		request.source(searchSourceBuilder);

		SearchResponse response = client.search(request, RequestOptions.DEFAULT);
		response.getHits().forEach(c -> {
			Map<String, Object> sourceAsMap = c.getSourceAsMap();
			PortfolioListData portfolio;
			portfolio = PortfolioListData
					.builder()
					.id(c.getId())
					.title(sourceAsMap.get("title").toString())
					.writer(sourceAsMap.get("writer").toString())
					.header(sourceAsMap.get("header").toString())
					.content(sourceAsMap.get("content").toString())
					.build();
			result.add(portfolio);
		});
		return result;
	}
	
	@Override
	public void save(Portfolio entity) throws IOException {
		IndexRequest request_portfolio = new IndexRequest(PODO, TYPE_NAME, entity.getId().toString());
		request_portfolio.source(XContentFactory.jsonBuilder().startObject()
				.field("title", entity.getTitle().toString())
				.field("header", entity.getHeader().toString())
				.field("content", entity.getDetail().getContent().toString())
				.field("major", entity.getMajor().toString())
				.field("writer", entity.getWriter().toString())
				.field("createDate", entity.getCreateDate().toString())
				.field("state", entity.getState().toString())
				.field("showType", entity.getShowType().toString())
				.endObject());
		client.index(request_portfolio, RequestOptions.DEFAULT);
	}

	@Override
	public void saveSearchKeyword(PortfolioSearchDTO dto) throws IOException {
		IndexRequest request_portfolio = new IndexRequest(PODO_SEARCH, TYPE_NAME);
		request_portfolio.source(XContentFactory.jsonBuilder().startObject()
				.field("keyword", dto.getKeyword())
				.field("user", dto.getEmail())
				.field("createDate", LocalDateTime.now().toString())
				.endObject());
		client.index(request_portfolio, RequestOptions.DEFAULT);
	}
	
	@Override
	public void update(Portfolio entity) throws IOException {
		UpdateRequest request = new UpdateRequest(PODO, entity.getId().toString());
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
		UpdateRequest request = new UpdateRequest(PODO, entity.toString());
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("state", "DELETE");
		Script inline = new Script(ScriptType.INLINE, "painless", "ctx._source.state = params.state", parameters);
		request.script(inline);
		client.update(request, RequestOptions.DEFAULT);
	}

	private Collection<? extends Bucket> getAggregationDatas(String index,String aggregationName,BoolQueryBuilder boolQueryBuilder,
			TermsAggregationBuilder aggregation_keyword) throws IOException {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(boolQueryBuilder);
		searchSourceBuilder.aggregation(aggregation_keyword);
		searchSourceBuilder.size(0);
		SearchRequest request = new SearchRequest(index);
		request.source(searchSourceBuilder);
		SearchResponse response = client.search(request, RequestOptions.DEFAULT);
		Terms terms = response.getAggregations().get(aggregationName);
		Collection<? extends Bucket> searchKeywords = terms.getBuckets();
		return searchKeywords;
	}
	
}
