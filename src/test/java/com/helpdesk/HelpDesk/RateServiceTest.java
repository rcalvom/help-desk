package com.helpdesk.HelpDesk;

import com.helpdesk.HelpDesk.DAO.FeedbackDAO;
import com.helpdesk.HelpDesk.DAO.RequestDAO;
import com.helpdesk.HelpDesk.Models.Feedback;
import com.helpdesk.HelpDesk.Models.Request;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RateServiceTest {

    public final boolean SUCCESSFUL_RATED_SERVICE = true;
    public final boolean FAILED_RATED_SERVICE = false;

    private RequestDAO requestDAO;
    private FeedbackDAO feedbackDAO;

    @Test
    void RateService() {
        Request request = requestDAO.selectById("FI0001");
        Feedback feedback = new Feedback("", 10, true);
        feedbackDAO.insert(feedback);
    }
}
