package com.ukj.freelecboard.service;

import com.ukj.freelecboard.domain.comments.Comments;
import com.ukj.freelecboard.domain.comments.CommentsRepository;
import com.ukj.freelecboard.domain.posts.Posts;
import com.ukj.freelecboard.domain.posts.PostsRepository;
import com.ukj.freelecboard.web.dto.comments.CommentsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentsService {

    private final CommentsRepository commentsRepository;
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(Long postsId, CommentsSaveRequestDto requestDto) {
        Posts posts = postsRepository.findById(postsId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + postsId));

        requestDto.setPosts(posts);

        Comments comment = requestDto.toEntity();

        return commentsRepository.save(comment).getId();
    }

    @Transactional
    public void delete(Long id) {
        Comments entity = commentsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + id));
        commentsRepository.delete(entity);
    }
}
