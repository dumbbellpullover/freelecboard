package com.ukj.freelecboard.domain.comments.service;

import com.ukj.freelecboard.domain.comments.Comments;
import com.ukj.freelecboard.domain.comments.repository.CommentsRepository;
import com.ukj.freelecboard.domain.posts.Posts;
import com.ukj.freelecboard.domain.posts.repository.PostsRepository;
import com.ukj.freelecboard.domain.user.User;
import com.ukj.freelecboard.domain.user.repository.UserRepository;
import com.ukj.freelecboard.domain.comments.dto.CommentsResponseDto;
import com.ukj.freelecboard.domain.comments.dto.CommentsSaveRequestDto;
import com.ukj.freelecboard.domain.comments.dto.CommentsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
@RequiredArgsConstructor
public class CommentsService {

    private final CommentsRepository commentsRepository;
    private final PostsRepository postsRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentsResponseDto findById(Long id) {
        Comments comments = commentsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + id));
        return new CommentsResponseDto(comments);
    }

    @Transactional
    public Long save(Long postsId, CommentsSaveRequestDto requestDto) {
        Posts posts = postsRepository.findById(postsId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + postsId));

        User user = userRepository.findByUsername(requestDto.getAuthorName())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. userName=" + requestDto.getAuthorName()));

        requestDto.setPosts(posts);
        requestDto.setAuthor(user);

        Comments comment = requestDto.toEntity();

        return commentsRepository.save(comment).getId();
    }

    @Transactional
    public Long update(Long commentsId, @ModelAttribute CommentsUpdateRequestDto requestDto) {
        Comments comments = commentsRepository.findById(commentsId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + commentsId));

        comments.update(requestDto.getContent());
        return commentsId;
    }

    @Transactional
    public void delete(Long id) {
        Comments entity = commentsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + id));
        commentsRepository.delete(entity);
    }

}
