package com.mjc.school.service.implement;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.implement.NewsRepo;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.model.Tag;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsRequestDTO;
import com.mjc.school.service.dto.NewsResponseDTO;
import com.mjc.school.service.exceptions.ErrorCodes;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ValidatorException;
import com.mjc.school.service.map.NewsModelMapper;
import com.mjc.school.service.validator.Validator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class NewsService implements BaseService<NewsRequestDTO, NewsResponseDTO, Long> {
    private final NewsRepo newsRepository;
    private final BaseRepository<Tag, Long> tagRepository;
    private final NewsModelMapper newsMapper = Mappers.getMapper(NewsModelMapper.class);
    private final Validator validator;

    @Autowired
    public NewsService(NewsRepo newsRepository, BaseRepository<Tag, Long> tagRepository, Validator validator){
        this.newsRepository = newsRepository;
        this.tagRepository = tagRepository;
        this.validator = validator;
    }

    @Override
    public List<NewsResponseDTO> readAll() {
        return newsMapper.modelListToDtoList(newsRepository.readAll());
    }

    @Override
    public NewsResponseDTO readById(Long id) throws NotFoundException {
        checkNewsExist(id);
        News model = newsRepository.readById(id).get();
        return newsMapper.modelToDTO(model);
    }

    @Override
    public NewsResponseDTO create(NewsRequestDTO createRequest) throws ValidatorException, NotFoundException {
        validator.checkNewsDto(createRequest);
        List<Long> tagIds = createRequest.getNewsTagsIds();
        News model = newsMapper.dtoToModel(createRequest);
        for (Long id : tagIds){
            if (tagRepository.existById(id)){
                model.setNewsTags((List<Tag>) tagRepository.readById(id).get());
            }
        }
        News newModel = newsRepository.create(model);
        return newsMapper.modelToDTO(newModel);
    }

    @Override
    public NewsResponseDTO update(NewsRequestDTO updateRequest) throws ValidatorException, NotFoundException {
        validator.checkNewsDto(updateRequest);
        List<Long> tagIds = updateRequest.getNewsTagsIds();
        News model = newsMapper.dtoToModel(updateRequest);
        checkNewsExist(model.getId());
        for (Long id : tagIds){
            if (tagRepository.existById(id)){
                model.setNewsTags((List<Tag>) tagRepository.readById(id).get());
            }
        }
        News updatedModel = newsRepository.update(model);

        return newsMapper.modelToDTO(updatedModel);
    }

    @Override
    public boolean deleteById(Long id) throws NotFoundException {
        checkNewsExist(id);
        return newsRepository.deleteById(id);
    }

    public List<NewsResponseDTO> readByParams(Long tagId, String tagName, String authorName, String title, String content){
        return newsMapper.modelListToDtoList(newsRepository.readByParams(tagId, tagName, authorName, title, content));
    }


    private void checkNewsExist(Long id) throws NotFoundException {
        if (!newsRepository.existById(id)){
            throw new NotFoundException(String.format(ErrorCodes.NEWS_ID_DOES_NOT_EXIST.getMessage(),id));
        }
    }
}
