package com.mjc.school.service.implement;

import com.mjc.school.repository.implement.TagRepo;
import com.mjc.school.repository.model.Tag;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.TagsRequestDTO;
import com.mjc.school.service.dto.TagsResponseDTO;
import com.mjc.school.service.exceptions.ErrorCodes;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ValidatorException;
import com.mjc.school.service.map.TagModelMapper;
import com.mjc.school.service.validator.Validator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService implements BaseService<TagsRequestDTO, TagsResponseDTO, Long> {

    private final TagRepo tagRepo;
    private final Validator validator;
    private final TagModelMapper mapper = Mappers.getMapper(TagModelMapper.class);
    @Autowired
    public TagService(TagRepo tagRepo, Validator validator) {
        this.tagRepo = tagRepo;
        this.validator = validator;
    }

    @Override
    public List<TagsResponseDTO> readAll() {
        return mapper.modelListToDtoList(tagRepo.readAll());
    }

    @Override
    public TagsResponseDTO readById(Long id) throws NotFoundException {
        Tag model = tagRepo.readById(id).orElseThrow(() ->
                new NotFoundException(String.format(ErrorCodes.TAG_ID_DOES_NOT_EXIST.getMessage(), id)));
        return mapper.modelToDTO(model);
    }

    @Override
    public TagsResponseDTO create(TagsRequestDTO createRequest) throws ValidatorException {
        validator.checkTagDto(createRequest);
        Tag model = mapper.dtoToModel(createRequest);
        tagRepo.create(model);
        return mapper.modelToDTO(model);
    }

    @Override
    public TagsResponseDTO update(TagsRequestDTO updateRequest) throws ValidatorException, NotFoundException {
        Tag model = mapper.dtoToModel(updateRequest);
        checkExist(model.getId());
        validator.checkTagDto(updateRequest);
        Tag updatedModel = tagRepo.update(model);
        return mapper.modelToDTO(updatedModel);
    }

    @Override
    public boolean deleteById(Long id) throws NotFoundException {
        checkExist(id);
        return tagRepo.deleteById(id);
    }

    private void checkExist(Long id) throws NotFoundException {
        if (!tagRepo.existById(id)){
            throw new NotFoundException(String.format(ErrorCodes.TAG_ID_DOES_NOT_EXIST.getMessage(), id));
        }
    }
}
