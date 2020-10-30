package com.helpdesk.HelpDesk;

import com.helpdesk.HelpDesk.DAO.RequestDAO;
import com.helpdesk.HelpDesk.DAO.UserDAO;
import com.helpdesk.HelpDesk.Models.Category;
import com.helpdesk.HelpDesk.Models.Request;
import com.helpdesk.HelpDesk.Models.User;
import com.helpdesk.HelpDesk.Repository.RequestRepository;
import com.helpdesk.HelpDesk.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class RequestTest {



    public final boolean SUCCESSFUL_REQUEST_CREATION = true;
    public final boolean FAILED_REQUEST_CREATION = false;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RequestRepository requestRepository;

    @Test
    public void UserRequest1() {
        RequestDAO requestDAO = new RequestDAO(requestRepository);
        UserDAO userDAO = new UserDAO(userRepository);
        Iterable<User> user = userDAO.selectUser();
        System.out.println(user);
        Request request = new Request("Daño",user.iterator().next());
        request.setId("f10");
        assertEquals(SUCCESSFUL_REQUEST_CREATION, requestDAO.insert(request));
    }
    @Test
    public void UserRequest2() {
        RequestDAO requestDAO = new RequestDAO(requestRepository);
        UserDAO userDAO = new UserDAO(userRepository);
        Iterable<User> user = userDAO.selectUser();
        System.out.println(user);
        Request request = new Request("Daño",user.iterator().next());
        assertEquals(FAILED_REQUEST_CREATION, requestDAO.insert(request));
    }
}
