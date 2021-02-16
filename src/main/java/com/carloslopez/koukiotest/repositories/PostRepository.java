package com.carloslopez.koukiotest.repositories;


import com.carloslopez.koukiotest.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    Page<Post> findAll(Pageable pageable);

    Optional<Post> findById(int id);
}
