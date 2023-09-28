package com.ukj.freelecboard.domain.comments.repository;

import com.ukj.freelecboard.domain.comments.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
}
