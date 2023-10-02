package com.ukj.freelecboard.domain.posts.repository;

import com.ukj.freelecboard.domain.posts.PostsVoter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsVoterRepository extends JpaRepository<PostsVoter, Long> {
}
