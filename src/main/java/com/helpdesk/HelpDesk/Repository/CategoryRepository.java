package com.helpdesk.HelpDesk.Repository;

import com.helpdesk.HelpDesk.Models.Category;
import com.helpdesk.HelpDesk.Models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, String> {
    @Query(value = "SELECT * FROM category where category.name = ?1 ;", nativeQuery = true)
    public Iterable<Category> getCategoryByName(String name);
}
