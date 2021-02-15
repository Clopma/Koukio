package com.carloslopez.koukiotest.controllers;

import com.carloslopez.koukiotest.entities.Post;
import com.carloslopez.koukiotest.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostController {


    @Autowired
    PostRepository postRepository;

    public void updatePosts(List<Post> posts){

        postRepository.saveAll(posts);

    }


    public Iterable<Post> getLastPosts(int page, int size){
        return postRepository.findAll(PageRequest.of(page, size));

    }



}
