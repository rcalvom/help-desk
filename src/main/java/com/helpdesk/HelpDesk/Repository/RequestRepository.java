package com.helpdesk.HelpDesk.Repository;

import com.helpdesk.HelpDesk.Models.Request;
import com.helpdesk.HelpDesk.Models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RequestRepository extends CrudRepository<Request, String> {

    @Query(value = "select * from request where request.user_username like ?1 ;", nativeQuery = true)
    public Iterable<Request> getRequestByUser(String username);

    @Query(value = "select * from request where request.status like ?1 ;", nativeQuery = true)
    public Iterable<Request> getRequestByState(String status);

}
