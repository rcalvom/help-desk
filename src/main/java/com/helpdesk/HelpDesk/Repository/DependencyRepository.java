package com.helpdesk.HelpDesk.Repository;

import com.helpdesk.HelpDesk.Models.Dependency;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DependencyRepository extends CrudRepository<Dependency, String> {
    @Query(value = "select * from dependency where dependency.name like ?1 ;", nativeQuery = true)
    Iterable<Dependency> getName(String name);
}
