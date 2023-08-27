package com.mjc.school.service.validator;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.service.dto.AuthorRequestDTO;
import com.mjc.school.service.dto.NewsRequestDTO;
import com.mjc.school.service.dto.TagsRequestDTO;
import com.mjc.school.service.exceptions.ErrorCodes;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ValidatorException;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    private final BaseRepository<Author, Long> authorRepo;

    private static final int TITLE_MAX_LENGTH = 30;
    private static final int TITLE_MIN_LENGTH = 5;
    private static final int CONTENT_MIN_LENGTH = 5;
    private static final int CONTENT_MAX_LENGTH = 255;
    private static final int AUTHOR_NAME_MIN_LENGTH = 3;
    private static final int AUTHOR_NAME_MAX_LENGTH = 15;
    private static final int TAG_MIN_LENGTH = 3;
    private static final int TAG_MAX_LENGTH = 15;

    public Validator(BaseRepository<Author, Long> authorRepo) {
        this.authorRepo = authorRepo;
    }

    public void checkNewsDto(NewsRequestDTO dto) throws NotFoundException, ValidatorException {

        validateLength(dto.getTitle(), TITLE_MIN_LENGTH, TITLE_MAX_LENGTH, "CHECK_TITLE_LENGTH");
        validateLength(dto.getContent(), CONTENT_MIN_LENGTH, CONTENT_MAX_LENGTH, "CHECK_CONTENT_LENGTH");
        long authorId = dto.getAuthorId();
        if (!authorRepo.existById(authorId)){
            throw new NotFoundException(String.format(ErrorCodes.AUTHOR_ID_DOES_NOT_EXIST.getMessage(),authorId));
        }
    }

    public void checkAuthorDto(AuthorRequestDTO dto) throws ValidatorException {
        validateLength(dto.getName(), AUTHOR_NAME_MIN_LENGTH, AUTHOR_NAME_MAX_LENGTH, "CHECK_AUTHOR_NAME_LENGTH");
    }

    public void checkTagDto(TagsRequestDTO dto) throws ValidatorException {
        validateLength(dto.getName(), TAG_MIN_LENGTH, TAG_MAX_LENGTH, "CHECK_TAG_NAME_LENGTH");
    }


    private void validateLength(String text, int minLength, int maxLength, String error) throws ValidatorException {
        if (text.length() < minLength || (text.length() > maxLength)){
            throw new ValidatorException(String.format(ErrorCodes.valueOf(error).getMessage(), minLength, maxLength, text));
        }
    }
}
