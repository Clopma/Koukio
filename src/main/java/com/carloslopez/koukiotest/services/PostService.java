package com.carloslopez.koukiotest.services;

import com.carloslopez.koukiotest.entities.Post;

import java.util.List;

public interface PostService {

    void updatePosts(List<Post> posts);

    Iterable<Post> getLastPosts(int page, int size);

}
