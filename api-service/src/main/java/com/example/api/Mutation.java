package com.example.api;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import graphql.servlet.GraphQLMutationProvider;
import org.springframework.stereotype.Service;

import javax.websocket.server.ServerEndpoint;

@Service
public class Mutation implements GraphQLMutationResolver {
    public String test() {
        return "I am graphql test mutation";
    }
}
