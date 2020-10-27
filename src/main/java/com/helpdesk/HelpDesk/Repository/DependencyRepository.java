package com.helpdesk.HelpDesk.Repository;

import com.helpdesk.HelpDesk.Models.Dependency;
import org.springframework.data.repository.CrudRepository;

public interface DependencyRepository extends CrudRepository<Dependency, String> {
}
