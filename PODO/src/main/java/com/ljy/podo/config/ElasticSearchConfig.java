package com.ljy.podo.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ljy.customConfig.EnableElasticsearchInitModule;

@Configuration
@EnableElasticsearchInitModule(init = false)
public class ElasticSearchConfig {

	@Value("${spring.elasticsearch.host}")
	private String host;

	@Value("${spring.elasticsearch.port}")
	private int port;
	
	@Value("${spring.elasticsearch.type}")
	private String type;
	
	@Bean
	public RestHighLevelClient client() {
		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("username", "password"));
		RestClientBuilder builder = RestClient.builder(new HttpHost(host, port, type)).setHttpClientConfigCallback(
				httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

		RestHighLevelClient client = new RestHighLevelClient(builder);
		return client;
	}
}
