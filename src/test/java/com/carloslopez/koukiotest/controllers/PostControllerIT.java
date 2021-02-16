package com.carloslopez.koukiotest.controllers;

import com.carloslopez.koukiotest.KoukiotestApplication;
import com.carloslopez.koukiotest.entities.Post;
import com.carloslopez.koukiotest.repositories.PostRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@Transactional //Rolls back changes after each test
@SpringBootTest(classes = KoukiotestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerIT {

    @Autowired
    PostController postController;

    @Autowired
    PostRepository postRepository;

    private static final int ID = 99999;
    private static final String NEW_TITLE = "Wekdienst 14/2";

    @Test
    public void insertsNewPostIfNotPresentInDatabase() {

        Optional<Post> post = postRepository.findById(ID);
        assertFalse(post.isPresent());

        List<Post> posts = Collections.singletonList(getExamplePost());
        postController.updatePosts(posts);

        post = postRepository.findById(ID);
        assertTrue(post.isPresent());
    }

    @Test
    public void updtatesNewPostIfPresentInDatabase() {

        Optional<Post> post = postRepository.findById(ID);
        assertFalse(post.isPresent());

        Post newPost = getExamplePost();
        postRepository.save(newPost);

        newPost.setTitle(NEW_TITLE);
        postController.updatePosts(Collections.singletonList(newPost));

        Optional<Post> updatedPost = postRepository.findById(ID);
        assertTrue(updatedPost.isPresent());
        assertEquals(NEW_TITLE, updatedPost.get().getTitle());
    }


    private Post getExamplePost() {
        return Post.builder()
                .id(ID)
                .title("Wekdienst 14/2: Waarschijnlijk laatste schaatsdag • Verkiezingen Catalonië")
                .description("<p>In Egypte zijn de restanten van een 5000 jaar oude bierbrouwerij ontdekt</p>")
                .imageUrl("https://cdn.nos.nl/image/2021/02/14/715299/1008x567.jpg")
                .publication(DateUtils.addYears(new Date(), -500))
                .build();
    }

}
