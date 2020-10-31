package com.helpdesk.HelpDesk.Repository;

import com.helpdesk.HelpDesk.Models.Request;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RequestRepository extends CrudRepository<Request, String> {

    @Query(value = "select * from request where request.user_username like ?1 ;", nativeQuery = true)
    Iterable<Request> getRequestByUser(String username);

    @Query(value = "select * from request where request.status like ?1 ;", nativeQuery = true)
    Iterable<Request> getRequestByState(String status);

    @Query(value = "select * from request where request.id like ?1 ;", nativeQuery = true)
    Iterable<Request> getRequestById(String id);

    @Query(value = "select * from request where id in (select id from agent_request where username like ?1 )", nativeQuery = true)
    Iterable<Request> getRequestByAgent(String username);
}
