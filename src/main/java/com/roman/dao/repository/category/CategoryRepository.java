package com.roman.dao.repository.category;

import com.roman.dao.entity.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>, CustomCategoryRepository {

    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD,attributePaths = {"news"})
    public Category findCategoryWithNewsById(Long id);
}
