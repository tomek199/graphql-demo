package com.example.api.users;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.example.api.documents.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserResolver implements GraphQLResolver<User> {
    private static final Logger LOG = LoggerFactory.getLogger(UserResolver.class);

    private final RestTemplate restTemplate;

    public UserResolver(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Document> documents(User user) {
        LOG.info("Query documents for user {}", user.id());
        return restTemplate.exchange(
                "http://localhost:8092/users/" + user.id() + "/documents",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Document>>() { }
        ).getBody();
    }
}
