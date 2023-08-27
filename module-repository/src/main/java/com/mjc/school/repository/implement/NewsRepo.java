package com.mjc.school.repository.implement;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.model.Tag;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

@Repository public class NewsRepo implements BaseRepository<News, Long > {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<News> readAll() {
        return entityManager.createQuery("select n from NewsModel n", News.class).getResultList();
    }

    @Override
    public Optional<News> readById(Long id) {
        return Optional.ofNullable(entityManager.find(News.class, id));
    }

    @Override
    public News create(News entity) {
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }

    @Override
    public News update(News entity) {
        News model = entityManager.find(News.class, entity.getId());
        model.setTitle(entity.getTitle());
        model.setContent(entity.getContent());
        model.setLastUpdateDate(entity.getLastUpdateDate());
        model.setAuthor(entity.getAuthor());
        model.setNewsTags(entity.getNewsTags());
        entityManager.flush();
        return model;
    }

    @Override
    public boolean deleteById(Long id) {
        if (existById(id)){
            entityManager.createQuery("delete from NewsModel n where n.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            return true;
        }
        return false;
    }

    @Override
    public boolean existById(Long id) {
        return entityManager.find(News.class, id) != null;
    }

    public List<News> readByParams(Long tagId, String tagName, String authorName, String title, String content){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<News> cr = cb.createQuery(News.class);
        Root<News> root = cr.from(News.class);

        Join<News, Tag> tags = root.join("tags");
        Join<News, Tag> author = root.join("author");

        Predicate tagIdCheck = cb.equal(tags.get("id"), tagId);
        Predicate tagNameCheck = cb.equal(tags.get("name"), tagName);
        Predicate authorCheck = cb.equal(author.get("name"), authorName);
        Predicate titleCheck  = cb.equal(root.get("title"), title);
        Predicate contentCheck  = cb.equal(root.get("content"), content);

        if (tagId != null) cr.select(root).where(tagIdCheck);
        if (!tagName.isBlank()) cr.select(root).where(tagNameCheck);
        if (!authorName.isBlank()) cr.select(root).where(authorCheck);
        if (!title.isBlank()) cr.select(root).where(titleCheck);
        if (!content.isBlank()) cr.select(root).where(contentCheck);

        TypedQuery<News> query = entityManager.createQuery(cr);
        return query.getResultList();
    }
}
