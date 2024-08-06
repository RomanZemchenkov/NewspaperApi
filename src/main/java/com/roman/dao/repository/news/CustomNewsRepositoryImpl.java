package com.roman.dao.repository.news;

import com.roman.dao.entity.News;
import com.roman.dao.filter.Predicate_;
import com.roman.dao.filter.news.NewsFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public class CustomNewsRepositoryImpl implements CustomNewsRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean deleteNewsById(Long id) {
        News news = entityManager.find(News.class, id);
        if(news != null){
            entityManager.remove(news);
            return true;
        }
        return false;
    }

    @Override
    public List<News> findNewsByFilter(NewsFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<News> query = cb.createQuery(News.class);
        Root<News> root = query.from(News.class);

        query.where(cb.and(predicates(cb,root,filter)));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<News> findAllWithCategory() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<News> query = cb.createQuery(News.class);
        Root<News> root = query.from(News.class);

        TypedQuery<News> query1 = entityManager.createQuery(query);
        query1.setHint("javax.persistence.loadgraph",entityManager.getEntityGraph("News.withCategory"));

        return query1.getResultList();
    }

    private Predicate[] predicates(CriteriaBuilder cb,Root<News> root,NewsFilter filter){
        Predicate_ predicate = Predicate_.of();
        String title = filter.getTitle();
        if(title != null && !title.isEmpty()){
            predicate.and(title, t -> cb.equal(root.get("title"),t));
        }
        String categoryId = filter.getCategoryId();
        if(categoryId != null && !categoryId.isEmpty()){
            predicate.and(categoryId,ci -> cb.equal(root.get("category").get("id"),ci));
        }
        return predicate.finish();
    }


}
