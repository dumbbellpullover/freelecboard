package com.ukj.freelecboard.domain.posts.repository;

import com.ukj.freelecboard.domain.posts.dto.PostsListResponseDto;
import com.ukj.freelecboard.domain.posts.dto.PostsSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostsRepositoryCustom {

    List<PostsListResponseDto> search(PostsSearchCondition condition);

    Page<PostsListResponseDto> searchPage(PostsSearchCondition condition, Pageable pageable);
}
