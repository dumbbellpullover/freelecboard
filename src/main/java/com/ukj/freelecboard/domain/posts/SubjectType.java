package com.ukj.freelecboard.domain.posts;

public enum SubjectType {
    TITLE("제목"),
    CONTENT("내용"),
    AUTHOR("작성자");

    public final String description;

    SubjectType(String description) {
        this.description = description;
    }
}
