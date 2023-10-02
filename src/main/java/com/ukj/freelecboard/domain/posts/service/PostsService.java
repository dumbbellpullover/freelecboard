package com.ukj.freelecboard.domain.posts.service;

import com.ukj.freelecboard.domain.posts.Posts;
import com.ukj.freelecboard.domain.posts.repository.PostsRepository;
import com.ukj.freelecboard.domain.posts.PostsVoter;
import com.ukj.freelecboard.domain.posts.repository.PostsVoterRepository;
import com.ukj.freelecboard.domain.user.User;
import com.ukj.freelecboard.domain.user.repository.UserRepository;
import com.ukj.freelecboard.domain.posts.dto.PostsListResponseDto;
import com.ukj.freelecboard.domain.posts.dto.PostsResponseDto;
import com.ukj.freelecboard.domain.posts.dto.PostsSaveRequestDto;
import com.ukj.freelecboard.domain.posts.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final PostsVoterRepository postsVoterRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getAuthorName())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. userName=" + requestDto.getAuthorName()));

        requestDto.setAuthor(user);
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

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
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
        postsRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        postsRepository.deleteAll();
    }

    @Transactional
    public void vote(Long id, String voterName) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. postsId=" + id));

        User voter = userRepository.findByUsername(voterName)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자는 존재하지 않습니다. userName=" + voterName));

        postsVoterRepository.save(PostsVoter.createPostsVoter(posts, voter));
        posts.increaseVotesCount();
    }
}
