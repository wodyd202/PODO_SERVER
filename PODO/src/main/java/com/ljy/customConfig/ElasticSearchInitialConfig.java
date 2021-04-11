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

	private final String PODO = "podo";
	private final String PODO_SEARCH = "podo-search";

	@Autowired
	private RestHighLevelClient client;

	@SuppressWarnings("deprecation")
	@PostConstruct
	public void init() throws Exception {
		try {
			DeleteIndexRequest request_1 = new DeleteIndexRequest(PODO);
			client.indices().delete(request_1, RequestOptions.DEFAULT);

			DeleteIndexRequest request_2 = new DeleteIndexRequest(PODO_SEARCH);
			client.indices().delete(request_2, RequestOptions.DEFAULT);
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
		XContentBuilder mappingBuilder_podo = XContentFactory.jsonBuilder()
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
							.startObject("header")
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
		XContentBuilder mappingBuilder_podo_search = XContentFactory.jsonBuilder()
				.startObject()
					.startObject("properties")
						.startObject("keyword")
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
		
		  CreateIndexRequest createIndex_1 = new CreateIndexRequest(PODO);
		  CreateIndexRequest createIndex_2 = new CreateIndexRequest(PODO_SEARCH);
          createIndex_1.settings(settingsBuilder);
          createIndex_1.mapping("_doc", mappingBuilder_podo);
          client.indices().create(createIndex_1, RequestOptions.DEFAULT);

          createIndex_2.settings(settingsBuilder);
          createIndex_2.mapping("_doc", mappingBuilder_podo_search);
          client.indices().create(createIndex_2, RequestOptions.DEFAULT);
	}
}
