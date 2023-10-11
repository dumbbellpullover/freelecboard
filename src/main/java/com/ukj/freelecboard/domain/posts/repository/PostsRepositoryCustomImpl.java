package com.ukj.freelecboard.domain.posts.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ukj.freelecboard.domain.posts.dto.PostsListResponseDto;
import com.ukj.freelecboard.domain.posts.dto.PostsSearchCondition;
import com.ukj.freelecboard.domain.posts.dto.QPostsListResponseDto;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.ukj.freelecboard.domain.posts.QPosts.*;
import static com.ukj.freelecboard.domain.user.QUser.*;

public class PostsRepositoryCustomImpl implements PostsRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PostsRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<PostsListResponseDto> search(PostsSearchCondition condition) {
        return queryFactory
                .select(new QPostsListResponseDto(
                        posts.id,
                        posts.title,
                        user.username,
                        posts.createdDate,
                        posts.commentsSize))
                .from(posts)
                .join(user)
                .on(posts.author.id.eq(user.id))
                .where(
                        titleContains(condition.getTitle())
                )
                .fetch();
    }

    /**
     * posts, user 전체를 조인해서 where title 을 하는 것 보다
     * posts 에서 where 로 title 을 먼저 찾고 조인하는 것이 성능상 좋음
     */
    @Override
    public Page<PostsListResponseDto> searchPage(PostsSearchCondition condition, Pageable pageable) {

        List<PostsListResponseDto> content = queryFactory
                .select(new QPostsListResponseDto(
                        posts.id,
                        posts.title,
                        user.username, // user 와 조인 고려
                        posts.createdDate,
                        posts.commentsSize))
                .from(posts)
                .join(user)
                .on(posts.author.id.eq(user.id))
                .where(
                        titleContains(condition.getTitle())
                )
                .orderBy(posts.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(posts.count())
                .from(posts)
                .join(user)
                .on(posts.author.id.eq(user.id))
                .where(
                        titleContains(condition.getTitle())
                );

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchOne());
    }

    private BooleanExpression titleContains(String title) {
        return StringUtils.hasText(title) ? posts.title.like("%" + title + "%") : null;
    }
}
