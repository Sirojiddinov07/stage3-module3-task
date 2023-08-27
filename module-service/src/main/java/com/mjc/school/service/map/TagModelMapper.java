package com.mjc.school.service.map;

import com.mjc.school.repository.model.Tag;
import com.mjc.school.service.dto.TagsRequestDTO;
import com.mjc.school.service.dto.TagsResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface TagModelMapper {

    TagsResponseDTO modelToDTO(Tag model);

    List<TagsResponseDTO> modelListToDtoList(List<Tag> modelList);

    @Mapping(target = "news", ignore = true)
    Tag dtoToModel(TagsRequestDTO dto);

}
