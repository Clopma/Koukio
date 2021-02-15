package com.carloslopez.koukiotest.graphql.resolvers;

import com.carloslopez.koukiotest.controllers.PostController;
import com.carloslopez.koukiotest.entities.Post;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Query implements GraphQLQueryResolver {

    @Autowired
    private PostController postController;

    public Iterable<Post> getLastPosts(int page, int size) {
        return postController.getLastPosts(page, size);
    }

}