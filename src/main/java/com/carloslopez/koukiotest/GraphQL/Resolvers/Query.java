package com.carloslopez.koukiotest.GraphQL.Resolvers;

import com.carloslopez.koukiotest.Controllers.PostController;
import com.carloslopez.koukiotest.Entities.Post;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.print.Pageable;

@Component
public class Query implements GraphQLQueryResolver {

    @Autowired
    private PostController postController;

    public Iterable<Post> getLastPosts(int page, int size) {
        return postController.getLastPosts(page, size);
    }

}