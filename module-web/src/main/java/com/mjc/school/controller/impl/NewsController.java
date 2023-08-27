package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsRequestDTO;
import com.mjc.school.service.dto.NewsResponseDTO;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ValidatorException;
import com.mjc.school.service.implement.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller
public class NewsController implements BaseController<NewsRequestDTO, NewsResponseDTO, Long> {
    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }


    @Override
    public List<NewsResponseDTO> readAll() {
        return newsService.readAll();
    }


    @Override
    public NewsResponseDTO readById(Long id) throws NotFoundException {
        return newsService.readById(id);
    }


    @Override
    public NewsResponseDTO create(NewsRequestDTO createRequest) throws ValidatorException, NotFoundException {
        return newsService.create(createRequest);
    }


    @Override
    public NewsResponseDTO update(NewsRequestDTO updateRequest) throws ValidatorException, NotFoundException {
        return newsService.update(updateRequest);
    }


    @Override
    public boolean deleteById(Long id) throws NotFoundException {
        return newsService.deleteById(id);
    }

    public List<NewsResponseDTO> readByParams(Long tagId, String tagName, String authorName, String title, String content){
        return newsService.readByParams(tagId, tagName, authorName, title, content);
    }
}
