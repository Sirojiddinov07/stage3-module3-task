package com.mjc.school.service.dto;


import java.util.List;

public class NewsResponseDTO {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private List<Long> newsTagsIds;

    @Override
    public String toString() {
        return "NewsResponseDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", authorId=" + authorId +
                ", newsTagsIds=" + newsTagsIds +
                '}';
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

