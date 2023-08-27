package com.mjc.school.controller;

import com.mjc.school.controller.actions.CommandHandler;
import com.mjc.school.controller.impl.AuthorController;
import com.mjc.school.controller.impl.NewsController;
import com.mjc.school.controller.impl.TagController;
import com.mjc.school.service.dto.AuthorRequestDTO;
import com.mjc.school.service.dto.NewsRequestDTO;
import com.mjc.school.service.dto.NewsResponseDTO;
import com.mjc.school.service.dto.TagsRequestDTO;
import com.mjc.school.service.exceptions.ErrorCodes;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ControllerHandler {
    private final NewsController newsController;
    private final AuthorController authorController;
    private final TagController tagController;

    @Autowired
    public ControllerHandler(NewsController newsController, AuthorController authorController, TagController tagController) {
        this.newsController = newsController;
        this.authorController = authorController;
        this.tagController = tagController;
    }

    @CommandHandler( code = 1)
    public void getAllNews(){
        System.out.println(Operations.GET_ALL_NEWS.getOperation());
        newsController.readAll().forEach(System.out::println);
    }

    @CommandHandler( code = 2)
    public void getNewsById() throws NotFoundException, ValidatorException {
        System.out.println(Operations.GET_NEWS_BY_ID.getOperation());
        Long id = readNumber("News Id");
        System.out.println(newsController.readById(id));
    }

    @CommandHandler(code = 3)
    public void createNews() throws ValidatorException, NotFoundException {
        System.out.println(Operations.CREATE_NEWS.getOperation());
        NewsRequestDTO dto = readNewsValues(null);
        System.out.println(newsController.create(dto));
    }

    @CommandHandler(code = 4)
    public void updateNews() throws ValidatorException, NotFoundException {
        System.out.println(Operations.UPDATE_NEWS.getOperation());
        Long id = readNumber("News id");
        NewsRequestDTO dto = readNewsValues(id);
        System.out.println(newsController.update(dto));
    }

    @CommandHandler(code = 5)
    public void deleteNewsById() throws NotFoundException, ValidatorException {
        Long id = readNumber("News id");
        System.out.println(newsController.deleteById(id));
    }

    @CommandHandler( code = 6)
    public void getAllAuthors(){
        System.out.println(Operations.GET_ALL_AUTHORS.getOperation());
        authorController.readAll().forEach(System.out::println);
    }

    @CommandHandler( code = 7)
    public void getAuthorById() throws NotFoundException, ValidatorException {
        System.out.println(Operations.GET_AUTHOR_BY_ID.getOperation());
        Long id = readNumber("Author Id");
        System.out.println(authorController.readById(id));
    }

    @CommandHandler(code = 8)
    public void createAuthor() throws ValidatorException, NotFoundException {
        System.out.println(Operations.CREATE_AUTHOR.getOperation());
        AuthorRequestDTO dto = readAuthorValues(null);
        System.out.println(authorController.create(dto));
    }

    @CommandHandler(code = 9)
    public void updateAuthor() throws ValidatorException, NotFoundException {
        System.out.println(Operations.UPDATE_AUTHOR.getOperation());
        Long id = readNumber("Author id");
        AuthorRequestDTO dto = readAuthorValues(id);
        System.out.println(authorController.update(dto));
    }

    @CommandHandler(code = 10)
    public void deleteAuthorById() throws NotFoundException, ValidatorException {
        System.out.println(Operations.DELETE_AUTHOR_BY_ID.getOperation());
        Long id = readNumber("Author id");
        System.out.println(authorController.deleteById(id));
    }


    @CommandHandler( code = 11)
    public void getAllTags(){
        System.out.println(Operations.GET_ALL_TAGS.getOperation());
        tagController.readAll().forEach(System.out::println);
    }

    @CommandHandler( code = 12)
    public void getTagById() throws NotFoundException, ValidatorException {
        System.out.println(Operations.GET_TAG_BY_ID.getOperation());
        Long id = readNumber("Tag Id");
        System.out.println(tagController.readById(id));
    }

    @CommandHandler(code = 13)
    public void createTag() throws ValidatorException, NotFoundException {
        System.out.println(Operations.CREATE_TAG.getOperation());
        TagsRequestDTO dto = readTagValues(null);
        System.out.println(tagController.create(dto));
    }

    @CommandHandler(code = 14)
    public void updateTag() throws ValidatorException, NotFoundException {
        System.out.println(Operations.UPDATE_TAG.getOperation());
        Long id = readNumber("Tag id");
        TagsRequestDTO dto = readTagValues(id);
        System.out.println(tagController.update(dto));
    }

    @CommandHandler(code = 15)
    public void deleteTagById() throws NotFoundException, ValidatorException {
        System.out.println(Operations.DELETE_TAG_BY_ID.getOperation());
        Long id = readNumber("Tag id");
        System.out.println(tagController.deleteById(id));
    }

    @CommandHandler(code = 16)
    public void readAuthorByNewsId() throws NotFoundException, ValidatorException {
        System.out.println(Operations.READ_AUTHOR_BY_NEWS_ID.getOperation());
        Long newsId = readNumber("News id");
        Long authorId = newsController.readById(newsId).getAuthorId();
        System.out.println(authorController.readById(authorId));

    }

    @CommandHandler(code = 17)
    public void readTagByNewsId() throws NotFoundException, ValidatorException {
        System.out.println(Operations.READ_TAG_BY_NEWS_ID.getOperation());
        Long newsId = readNumber("News id");
        System.out.println(newsController.readById(newsId).getNewsTagsIds().toString());
    }

    @CommandHandler(code = 18)
    public void readNewsByParams(){
        Scanner sc = new Scanner(System.in);
        System.out.println(Operations.READ_NEWS_BY_PARAMS.getOperation());
        System.out.println("[Optional] Enter tag id:");
        Long tagId;
        String tagIdString = sc.nextLine();
        if (!tagIdString.isBlank()){
            tagId = Long.parseLong(tagIdString);
        } else {
            tagId = null;
        }
        System.out.println("[Optional] Enter tag name:");
        String tagName = sc.nextLine();
        System.out.println("[Optional] Enter author name:");
        String authorName = sc.nextLine();
        System.out.println("[Optional] Enter news title: ");
        String title = sc.nextLine();
        System.out.println("[Optional] Enter news content:");
        String content = sc.nextLine();

        List<NewsResponseDTO> news = newsController.readByParams(tagId, tagName, authorName, title, content);
        if (news.isEmpty())  {
            System.out.println("Cannot find news with provided parameters.");
        } else {
            news.forEach(System.out::println);
        }
    }

    private Long readNumber(String type) throws ValidatorException {
        System.out.println("Enter " + type +":");
        Scanner scr = new Scanner(System.in);
        try {
            return scr.nextLong();
        } catch (Exception e){
            throw new ValidatorException(String.format(ErrorCodes.VALIDATE_NEGATIVE_OR_NULL_NUMBER.getMessage(), type));
        }
    }

    private TagsRequestDTO readTagValues(Long id) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter tag name: ");
        String name = sc.nextLine();
        return new TagsRequestDTO(id, name);
    }

    private AuthorRequestDTO readAuthorValues(Long id) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter author name: ");
        String name = sc.nextLine();
        return new AuthorRequestDTO(id, name);
    }

    private NewsRequestDTO readNewsValues(Long id) throws ValidatorException {
        Scanner scr = new Scanner(System.in);
        System.out.println("Enter news title:");
        String title = scr.nextLine();
        System.out.println("Enter news content:");
        String content = scr.nextLine();
        Long authorId = readNumber("Author Id");
        System.out.println("[Optional] Enter tag ids (seperated by comma):  ");
        String tags = scr.nextLine();
        Set<Long> tagSet = new HashSet<>();
        if (!tags.isBlank()){
            String[] tag_ids = tags.split(",");
            try {
                for (String s: tag_ids){
                    tagSet.add(Long.parseLong(s.trim()));
                }
            } catch (InputMismatchException e){
                throw new ValidatorException(String.format(ErrorCodes.VALIDATE_NEGATIVE_OR_NULL_NUMBER.getMessage(), "Tag id"));
            }
        }
        return new NewsRequestDTO(id, title, content, authorId, tagSet);
    }
}
