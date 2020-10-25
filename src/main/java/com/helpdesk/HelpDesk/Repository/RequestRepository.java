package com.helpdesk.HelpDesk.Repository;

import com.helpdesk.HelpDesk.Models.Request;
import org.springframework.data.repository.CrudRepository;

public interface RequestRepository extends CrudRepository<Request, String> {
}
