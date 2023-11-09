package com.ukj.freelecboard.domain.posts.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ukj.freelecboard.domain.posts.SubjectType;
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
                        contains(condition)
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
                        contains(condition)
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
                        contains(condition)
                );

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchOne());
    }

    private BooleanExpression contains(PostsSearchCondition condition) {
//        //검색시, 글 내용이 있고, 글 제목이 있는경우 or로 검색 요청을 보내는 쿼리
//        if(!boardContent.isEmpty() && !boardTitle.isEmpty()){
//            return qBoardEntity.boardTitle.contains(boardTitle).or(qBoardEntity.boardContent.contains(boardContent));
//        }

        //검색시, 글 제목만 있는 경우, 글 제목을 검색 요청하는 쿼리
        if (condition.getSubjectType() == SubjectType.TITLE){
            return StringUtils.hasText(condition.getKeyword()) ? posts.title.contains(condition.getKeyword()) : null;
        }

        //검색시, 글 내용만 있는 경우, 글 내용을 검색 요청하는 쿼리
        if (condition.getSubjectType() == SubjectType.CONTENT){
            return StringUtils.hasText(condition.getKeyword()) ? posts.content.contains(condition.getKeyword()) : null;
        }

        //검색시, 작성자 있는 경우, 작성자를 검색 요청하는 쿼리
        if (condition.getSubjectType() == SubjectType.AUTHOR){
            return StringUtils.hasText(condition.getKeyword()) ? user.username.contains(condition.getKeyword()) : null;
        }


        return null;
    }
}
