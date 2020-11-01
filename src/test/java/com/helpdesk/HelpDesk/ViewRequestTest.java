package com.helpdesk.HelpDesk;

import com.helpdesk.HelpDesk.DAO.CategoryDAO;
import com.helpdesk.HelpDesk.DAO.RequestDAO;
import com.helpdesk.HelpDesk.DAO.UserDAO;
import com.helpdesk.HelpDesk.Models.Request;
import com.helpdesk.HelpDesk.Models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ViewRequestTest {

    public final boolean VIEW_REQUEST_SUCCESSFUL = true;
    public final boolean VIEW_REQUEST_FAILED = false;

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RequestDAO requestDAO;


    @Test
    public void ViewRequestUser() {
        boolean flag = true;
        User user1 = userDAO.selectUser("user");
        if ( user1 == null){
            flag = false;
        }else{
            List<Request> requests = (List<Request>) requestDAO.selectByUser(user1);
            for (Request request: requests) {
                flag &= request.getUser().getUsername().equals(user1.getUsername());
            }
        }
        assertEquals(VIEW_REQUEST_SUCCESSFUL, flag);

        boolean flag1 = true;
        User user2 = userDAO.selectUser("u");
        if ( user2 == null ){
            flag1 = false;
        }else{
            List<Request> requests1 = (List<Request>) requestDAO.selectByUser(user2);
            for (Request request: requests1) {
                flag1 &= request.getUser().getUsername().equals(user2.getUsername());
            }
        }

        assertEquals(VIEW_REQUEST_FAILED, flag1);
    }

    @Test
    public void ViewRequestAgent(){
        boolean flag = true;
        User user1 = userDAO.selectAgent("agent1");
        if ( user1 == null){
            flag = false;
        }else{
            List<Request> requests = (List<Request>) requestDAO.selectByAgent(user1);
            for (Request request: requests) {
                boolean flag2 = false;
                for ( User user : request.getAgents() ) {
                    flag2 |= user.getUsername().equals(user1.getUsername());
                }
                flag &= flag2;
            }
        }
        assertEquals(VIEW_REQUEST_SUCCESSFUL, flag);

        boolean flag1 = true;
        User user2 = userDAO.selectAgent("a");
        if ( user2 == null){
            flag1 = false;
        }else{
            List<Request> requests = (List<Request>) requestDAO.selectByAgent(user2);
            for (Request request: requests) {
                boolean flag2 = false;
                for ( User user : request.getAgents() ) {
                    flag2 |= user.getUsername().equals(user2.getUsername());
                }
                flag1 &= flag2;
            }
        }
        assertEquals(VIEW_REQUEST_FAILED, flag1);
    }

    @Test
    public void ViewRequestAdmin(){
        User admin = userDAO.selectAdmin();
        List<Request> requests = (List<Request>) requestDAO.select();
        assertEquals(VIEW_REQUEST_SUCCESSFUL, admin != null);
        assertEquals(VIEW_REQUEST_SUCCESSFUL, requests != null);
    }
}
