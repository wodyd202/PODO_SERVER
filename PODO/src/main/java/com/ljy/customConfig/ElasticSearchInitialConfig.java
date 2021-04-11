package com.ljy.customConfig;

import javax.annotation.PostConstruct;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchInitialConfig {

	private final String PORTFOLIO = "podo";

	@Autowired
	private RestHighLevelClient client;

	@SuppressWarnings("deprecation")
	@PostConstruct
	public void init() throws Exception {
		try {
			DeleteIndexRequest request = new DeleteIndexRequest(PORTFOLIO);
			client.indices().delete(request, RequestOptions.DEFAULT);
		}catch (Exception e) {
		}

		XContentBuilder settingsBuilder = XContentFactory.jsonBuilder()
					.startObject()
							.startObject("analysis")
								.startObject("analyzer")
									.startObject("my_html_analyzer")
										.field("type", "custom")
										.field("tokenizer", "nori_tokenizer")
										.field("char_filter", new String[] {"html_strip"})
									.endObject()
									.startObject("parsed_analyzer")
										.field("type", "custom")
										.field("tokenizer", "keyword")
										.field("char_filter", new String[] {"html_strip"})
									.endObject()
						.endObject()
					.endObject()
				.endObject();
		XContentBuilder mappingBuilder = XContentFactory.jsonBuilder()
					.startObject()
						.startObject("properties")
							.startObject("content")
								.field("type", "text")
								.field("analyzer", "my_html_analyzer")
								.field("fielddata", "true")
								.startObject("fields")
									.startObject("parsed")
										.field("type", "text")
										.field("analyzer", "parsed_analyzer")
									.endObject()
								.endObject()
							.endObject()
							.startObject("title")
							.field("type", "text")
							.field("analyzer", "my_html_analyzer")
							.field("fielddata", "true")
							.startObject("fields")
							.startObject("parsed")
							.field("type", "text")
							.field("analyzer", "parsed_analyzer")
							.endObject()
							.endObject()
							.endObject()
							.endObject()
					.endObject(); 
		
		  CreateIndexRequest createIndex = new CreateIndexRequest(PORTFOLIO);
          createIndex.settings(settingsBuilder);
          createIndex.mapping("_doc", mappingBuilder);
          client.indices().create(createIndex, RequestOptions.DEFAULT);
	}
}
