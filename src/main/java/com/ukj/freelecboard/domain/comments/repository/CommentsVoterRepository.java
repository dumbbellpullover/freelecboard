package com.ukj.freelecboard.domain.comments.repository;

import com.ukj.freelecboard.domain.comments.CommentsVoter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsVoterRepository extends JpaRepository<CommentsVoter, Long> {
}
