package com.helpdesk.HelpDesk.Repository;

import com.helpdesk.HelpDesk.Models.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, String> {
    @Query(value = "SELECT * FROM category where category.name = ?1 ;", nativeQuery = true)
    Iterable<Category> getCategoryByName(String name);

    @Query(value = "SELECT * FROM category where (is_active = 1) ;", nativeQuery = true)
    Iterable<Category> getActiveCategories();
}
