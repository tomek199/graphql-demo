package com.example.api.users;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.api.documents.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UsersQuery implements GraphQLQueryResolver {
    private static final Logger LOG = LoggerFactory.getLogger(UsersQuery.class);

    private final RestTemplate restTemplate;

    public UsersQuery(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> users() {
        LOG.info("Query users list");
        return restTemplate.exchange(
                "http://localhost:8091/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() { }
        ).getBody();
    }

    public User user(String id) {
        LOG.info("Query user {}", id);
        try {
            return restTemplate.getForObject("http://localhost:8091/users/" + id, User.class);
        } catch (HttpClientErrorException e) {
            LOG.info("User {} is not found ", id);
            return null;
        }
    }
}
