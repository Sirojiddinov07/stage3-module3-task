package com.mjc.school.service.dto;

import java.util.List;
import java.util.Set;

public class NewsRequestDTO {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private List<Long> newsTagsIds;

    public NewsRequestDTO(Long id, String title, String content, Long authorId, Set<Long> tagSet) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public List<Long> getNewsTagsIds() {
        return newsTagsIds;
    }

    public void setNewsTagsIds(List<Long> newsTagsIds) {
        this.newsTagsIds = newsTagsIds;
    }
}