package com.carloslopez.koukiotest.graphql.resolvers;

import com.carloslopez.koukiotest.entities.Post;
import com.carloslopez.koukiotest.services.PostService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Query implements GraphQLQueryResolver {

    @Autowired
    private PostService postService;

    public Iterable<Post> getLastPosts(int page, int size) {
        return postService.getLastPosts(page, size);
    }
}