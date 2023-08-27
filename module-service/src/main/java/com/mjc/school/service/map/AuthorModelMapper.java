package com.mjc.school.service.map;

import com.mjc.school.repository.model.Author;
import com.mjc.school.service.dto.AuthorRequestDTO;
import com.mjc.school.service.dto.AuthorResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface AuthorModelMapper {
    AuthorResponseDTO modelToDTO(Author authorModel);
    List<AuthorResponseDTO> modelListToDtoList (List<Author> newsList);
    @Mappings(value = { @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "lastUpdateDate", ignore = true)})
    Author dtoToModel (AuthorRequestDTO requestDTO);
}
