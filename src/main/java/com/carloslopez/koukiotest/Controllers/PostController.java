package com.carloslopez.koukiotest.Controllers;

import com.carloslopez.koukiotest.Entities.Post;
import com.carloslopez.koukiotest.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Collections;
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
