package com.example.api.documents;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DocumentsQuery implements GraphQLQueryResolver {
	private static final Logger LOG = LoggerFactory.getLogger(DocumentsQuery.class);

	private final RestTemplate restTemplate;

	public DocumentsQuery(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public List<Document> documents() {
		LOG.info("Query documents list");
		return restTemplate.exchange(
				"http://localhost:8092/documents",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Document>>() { }
		).getBody();
	}

	public Document document(String id) {
		LOG.info("Query document {}", id);
		try {
			return restTemplate.getForObject("http://localhost:8092/documents/" + id, Document.class);
		} catch (HttpClientErrorException e) {
			LOG.info("Document {} is not found ", id);
			return null;
		}
	}
}
