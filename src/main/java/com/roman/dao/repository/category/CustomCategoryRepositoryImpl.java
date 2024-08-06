package com.roman.dao.repository.category;

import com.roman.dao.entity.Category;
import com.roman.dao.filter.Predicate_;
import com.roman.dao.filter.category.CategoryFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class CustomCategoryRepositoryImpl implements CustomCategoryRepository{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean deleteCategoryById(Long id) {
        Category category = entityManager.find(Category.class, id);
        if(category != null){
            entityManager.remove(category);
            return true;
        }
        return false;
    }

    @Override
    public List<Category> findCategoryByFilter(CategoryFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> query = cb.createQuery(Category.class);
        Root<Category> root = query.from(Category.class);

        Predicate[] predicates = predicates(cb, root, filter);
        query.where(cb.and(predicates));

        return entityManager.createQuery(query).getResultList();
    }

    private Predicate[] predicates(CriteriaBuilder cb, Root<Category> root,CategoryFilter filter){
        Predicate_ predicate = Predicate_.of();
        if (filter.getId() != null){
            predicate.and(filter.getId(),id -> cb.equal(root.get("id"),id));
        }
        if (filter.getTitle() != null && !filter.getTitle().isEmpty()){
            predicate.and(filter.getTitle(), title -> cb.equal(root.get("title"),title));
        }
        return predicate.finish();
    }
}
