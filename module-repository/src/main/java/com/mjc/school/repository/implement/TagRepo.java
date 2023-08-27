package com.mjc.school.repository.implement;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepo implements BaseRepository<Tag, Long> {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Tag> readAll() {
        return entityManager.createQuery("select name from Tag name", Tag.class).getResultList();
    }

    @Override
    public Optional<Tag> readById(Long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public Tag create(Tag entity) {
        entityManager.persist(entity);
        entityManager.flush();
        return entity;    }

    @Override
    public Tag update(Tag entity) {
        Tag model = entityManager.find(Tag.class, entity.getId());
        model.setName(entity.getName());
        entityManager.flush();
        return model;    }

    @Override
    public boolean deleteById(Long id) {
        Tag tag = entityManager.find(Tag.class, id);
        if (tag == null){
            return false;
        }
        entityManager.remove(tag);
        entityManager.flush();
        return true;    }

    @Override
    public boolean existById(Long id) {
        return entityManager.find(Tag.class, id) != null;
    }
}
