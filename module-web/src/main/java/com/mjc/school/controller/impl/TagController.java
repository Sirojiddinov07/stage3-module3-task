package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.TagsRequestDTO;
import com.mjc.school.service.dto.TagsResponseDTO;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TagController implements BaseController<TagsRequestDTO, TagsResponseDTO, Long> {

    private final BaseService<TagsRequestDTO, TagsResponseDTO, Long> service;

    @Autowired
    public TagController(BaseService<TagsRequestDTO, TagsResponseDTO, Long> service) {
        this.service = service;
    }

    @Override
    public List<TagsResponseDTO> readAll() {
        return service.readAll();
    }

    @Override
    public TagsResponseDTO readById(Long id) throws NotFoundException {
        return service.readById(id);
    }

    @Override
    public TagsResponseDTO create(TagsRequestDTO createRequest) throws ValidatorException, NotFoundException {
        return service.create(createRequest);
    }

    @Override
    public TagsResponseDTO update(TagsRequestDTO updateRequest) throws ValidatorException, NotFoundException {
        return service.update(updateRequest);
    }

    @Override
    public boolean deleteById(Long id) throws NotFoundException {
        return service.deleteById(id);
    }
}
