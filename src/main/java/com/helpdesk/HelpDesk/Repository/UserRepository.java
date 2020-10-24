package com.helpdesk.HelpDesk.Repository;

import com.helpdesk.HelpDesk.Models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
