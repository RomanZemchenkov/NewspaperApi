package com.roman.dao.repository.news;

import com.roman.dao.entity.News;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface NewsRepository extends JpaRepository<News,Long>, CustomNewsRepository {

    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD,attributePaths = {"category"})
    News findNewsWithCategoryById(Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM News w WHERE w.id = :id RETURNING id",nativeQuery = true)
    Long deleteNewsByIdReturnedId(Long id);
}
