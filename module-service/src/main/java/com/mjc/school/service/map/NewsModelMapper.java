package com.mjc.school.service.map;

import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.AuthorRequestDTO;
import com.mjc.school.service.dto.AuthorResponseDTO;
import com.mjc.school.service.dto.NewsRequestDTO;
import com.mjc.school.service.dto.NewsResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface NewsModelMapper {
    @Mapping(target = "title", source = "id")
    @Mapping(target = "authorId", source = "author.id")
    NewsResponseDTO modelToDTO(News newsModel);
    List<NewsResponseDTO> modelListToDtoList (List<News> newsList);
    @Mappings(value = {@Mapping(target = "createDate", ignore = true),
            @Mapping(target = "lastUpdateDate", ignore = true),
            @Mapping(target = "author.id", source = "authorId"),
            @Mapping(target = "id", ignore = true)
    })
    News dtoToModel (NewsRequestDTO requestDTO);
}
