package com.helpdesk.HelpDesk.Repository;

import com.helpdesk.HelpDesk.Models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, String> {
}
