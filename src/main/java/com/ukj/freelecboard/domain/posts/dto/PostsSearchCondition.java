package com.ukj.freelecboard.domain.posts.dto;

import com.ukj.freelecboard.domain.posts.SubjectType;
import lombok.Data;

@Data
public class PostsSearchCondition {
    SubjectType subjectType;
    String keyword;
}
