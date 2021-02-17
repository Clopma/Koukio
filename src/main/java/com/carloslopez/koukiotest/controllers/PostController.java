package com.carloslopez.koukiotest.controllers;

import com.carloslopez.koukiotest.entities.Post;
import com.carloslopez.koukiotest.repositories.PostRepository;
import com.carloslopez.koukiotest.services.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostController implements PostService {

    @Autowired
    PostRepository postRepository;

    Logger logger = LoggerFactory.getLogger(PostController.class);

    public void updatePosts(List<Post> posts) {
        logger.info("Updating posts");
        postRepository.saveAll(posts);
    }


    public Iterable<Post> getLastPosts(int page, int size) {
        return postRepository.findAll(PageRequest.of(page, size));
    }



}
