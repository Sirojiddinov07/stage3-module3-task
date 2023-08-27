package com.mjc.school.service.implement;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorRequestDTO;
import com.mjc.school.service.dto.AuthorResponseDTO;
import com.mjc.school.service.exceptions.ErrorCodes;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ValidatorException;
import com.mjc.school.service.map.AuthorModelMapper;
import com.mjc.school.service.validator.Validator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService implements BaseService<AuthorRequestDTO, AuthorResponseDTO, Long> {
    private final BaseRepository<Author, Long> authorRepository;
    private final Validator validator;
    private final AuthorModelMapper mapper = Mappers.getMapper(AuthorModelMapper.class);


    @Autowired
    public AuthorService(BaseRepository<Author, Long> authorRepository, Validator validator) {
        this.authorRepository = authorRepository;
        this.validator = validator;
    }

    @Override
    public List<AuthorResponseDTO> readAll() {
        return mapper.modelListToDtoList(authorRepository.readAll());
    }

    @Override
    public AuthorResponseDTO readById(Long id) throws NotFoundException {
        Author dto = authorRepository.readById(id).orElseThrow(() ->
                new NotFoundException(String.format(ErrorCodes.AUTHOR_ID_DOES_NOT_EXIST.getMessage(), id)));
        return mapper.modelToDTO(dto);    }

    @Override
    public AuthorResponseDTO create(AuthorRequestDTO createRequest) throws ValidatorException {
        validator.checkAuthorDto(createRequest);
        Author model = mapper.dtoToModel(createRequest);
        Author newModel = authorRepository.create(model);
        return mapper.modelToDTO(newModel);    }

    @Override
    public AuthorResponseDTO update(AuthorRequestDTO updateRequest) throws ValidatorException,
            NotFoundException {
        validator.checkAuthorDto(updateRequest);
        Author model = mapper.dtoToModel(updateRequest);
        readById(model.getId());
        Author updatedModel = authorRepository.update(model);
        return mapper.modelToDTO(updatedModel);    }

    @Override
    public boolean deleteById(Long id) throws NotFoundException {
        if (authorRepository.existById(id)){
            return authorRepository.deleteById(id);
        } else throw new NotFoundException(String.format(ErrorCodes.AUTHOR_ID_DOES_NOT_EXIST.getMessage(), id));
    }
}

