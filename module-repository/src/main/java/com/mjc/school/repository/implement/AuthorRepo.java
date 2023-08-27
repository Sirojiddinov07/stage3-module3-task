package com.mjc.school.repository.implement;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.Author;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepo implements BaseRepository<Author,Long> {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Author> readAll() {
        return entityManager.createQuery("select name from Author name", Author.class).getResultList();
    }

    @Override
    public Optional<Author> readById(Long id) {
        return Optional.ofNullable(entityManager.find(Author.class, id));
    }



    @Override
    public Author create(Author entity) {
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }


    @Override
    public Author update(Author entity) {
        Author model = entityManager.find(Author.class, entity.getId());
        model.setLastUpdateDate(entity.getLastUpdateDate());
        model.setName(entity.getName());
        entityManager.flush();
        return model;
    }


    @Override
    public boolean deleteById(Long id) {
        entityManager.remove(entityManager.find(Author.class, id));
        return true;
    }

    @Override
    public boolean existById(Long id) {
        Object o = entityManager.createQuery("select name from Author name where name.id = :id")
                .setParameter("id", id)
                .getSingleResult();
        return o != null;
    }

}
