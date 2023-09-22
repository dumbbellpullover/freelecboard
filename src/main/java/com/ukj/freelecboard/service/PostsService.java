package com.ukj.freelecboard.service;

import com.ukj.freelecboard.domain.posts.Posts;
import com.ukj.freelecboard.domain.posts.PostsRepository;
import com.ukj.freelecboard.web.dto.posts.PostsListResponseDto;
import com.ukj.freelecboard.web.dto.posts.PostsResponseDto;
import com.ukj.freelecboard.web.dto.posts.PostsSaveRequestDto;
import com.ukj.freelecboard.web.dto.posts.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public Page<PostsListResponseDto> findPagingList(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10);

        return postsRepository.findAll(pageable)
                .map(PostsListResponseDto::new);
    }



    @Transactional
    public void delete(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
        postsRepository.delete(entity);
    }

    @Transactional
    public void deleteAll() {
        postsRepository.deleteAll();
    }
}
