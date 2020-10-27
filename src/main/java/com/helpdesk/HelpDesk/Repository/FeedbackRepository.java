package com.helpdesk.HelpDesk.Repository;

import com.helpdesk.HelpDesk.Models.Feedback;
import org.springframework.data.repository.CrudRepository;

public interface FeedbackRepository extends CrudRepository<Feedback, Integer> {
}
