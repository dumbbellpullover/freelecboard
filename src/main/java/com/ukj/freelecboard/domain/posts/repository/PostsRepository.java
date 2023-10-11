package com.ukj.freelecboard.domain.posts.repository;

import com.ukj.freelecboard.domain.posts.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostsRepository extends JpaRepository<Posts, Long>, PostsRepositoryCustom {

    @Query("select p from Posts p order by p.id desc")
    Page<Posts> findAll(Pageable pageable);
}
