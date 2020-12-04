package com.helpdesk.HelpDesk;

import com.helpdesk.HelpDesk.DAO.FeedbackDAO;
import com.helpdesk.HelpDesk.DAO.RequestDAO;
import com.helpdesk.HelpDesk.Models.Feedback;
import com.helpdesk.HelpDesk.Models.Request;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RateServiceTest {

    public final boolean SUCCESSFUL_RATED_SERVICE = true;
    public final boolean FAILED_RATED_SERVICE = false;


    @Autowired
    private RequestDAO requestDAO;
    @Autowired
    private FeedbackDAO feedbackDAO;

    @Test
    void RateService() {
        Feedback feedback = new Feedback("uwu", 10, true);
        assertEquals(FAILED_RATED_SERVICE, feedbackDAO.insert(feedback)) ;

        feedback = new Feedback("uwu", 5, true);
        assertEquals(SUCCESSFUL_RATED_SERVICE, feedbackDAO.insert(feedback));


        Request request = requestDAO.selectById("FI00001");
        Request newRequest = new Request();
        newRequest.setId(request.getId());
        newRequest.setSpecification(request.getSpecification());
        newRequest.setCreationDate(request.getCreationDate());
        newRequest.setEndingDate(request.getEndingDate());
        newRequest.setStatus(Request.Status.CERRADO);
        newRequest.setInventoryPlate(request.getInventoryPlate());
        newRequest.setEquipmentNumber(request.getEquipmentNumber());
        newRequest.setUser(request.getUser());
        newRequest.setAgents(request.getAgents());
        newRequest.setCategory(request.getCategory());
        newRequest.setFeedback(feedback);
        assertEquals(SUCCESSFUL_RATED_SERVICE, requestDAO.update(request, newRequest));


        Feedback feedback2 = new Feedback("askdjlf", 4, true);
        Request request2 = requestDAO.selectById("FI00001");
        Request newRequest2 = new Request();
        newRequest2.setId(request2.getId());
        newRequest2.setSpecification(request2.getSpecification());
        newRequest2.setCreationDate(request2.getCreationDate());
        newRequest2.setEndingDate(request2.getEndingDate());
        newRequest2.setStatus(Request.Status.CERRADO);
        newRequest2.setInventoryPlate(request2.getInventoryPlate());
        newRequest2.setEquipmentNumber(request2.getEquipmentNumber());
        newRequest2.setUser(request2.getUser());
        newRequest2.setAgents(request2.getAgents());
        newRequest2.setCategory(request2.getCategory());
        newRequest2.setFeedback(feedback2);
        assertEquals(FAILED_RATED_SERVICE, requestDAO.update(request2, newRequest2));
    }
}
