package com.ljy.podo.portfolio.service;

import java.io.IOException;
import java.util.Collection;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PortfolioLoadServiceTest {
	
	@Autowired
	private RestHighLevelClient client;

	@Test
	void load_1() throws IOException {
		TermsAggregationBuilder aggregationBuilder =  AggregationBuilders
															.terms("keywords")
															.field("keyword")
															.size(3);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.aggregation(aggregationBuilder);
		searchSourceBuilder.size(0);
		SearchRequest request = new SearchRequest("podo-search");
		request.source(searchSourceBuilder);

		SearchResponse response = client.search(request, RequestOptions.DEFAULT);
		Terms terms = response.getAggregations().get("keywords");
		Collection<? extends Bucket> buckets = terms.getBuckets();
		buckets.forEach(c->{
			System.out.println(c.getKey().toString());
		});
	}
}
