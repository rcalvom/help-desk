package com.helpdesk.HelpDesk.Repository;

import com.helpdesk.HelpDesk.Models.BoundingType;
import com.helpdesk.HelpDesk.Models.Request;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BoundingTypeRepository extends CrudRepository<BoundingType, String> {
    @Query(value = "select * from bounding_type where bounding_type.name like ?1 ;", nativeQuery = true)
    public Iterable<BoundingType> getName(String name);
}
