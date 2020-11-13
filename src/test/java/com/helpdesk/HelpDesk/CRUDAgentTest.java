package com.helpdesk.HelpDesk;

import com.helpdesk.HelpDesk.DAO.UserDAO;
import com.helpdesk.HelpDesk.Models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.w3c.dom.UserDataHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CRUDAgentTest {

    public final boolean SUCCESFUL_AGENT_ACTION = true;
    public final boolean FAILED_AGENT_ACTION = false;

    @Autowired
    private UserDAO userDAO;
    @Test
    void DeactiveAgent() {

        User user = userDAO.selectPerson("agent3");
        User nUser = new User(user.getUsername(), user.getName(), user.getBoundingType(), user.getDependency());
        nUser.setAgent(false);
        System.out.println(user);
        System.out.println(nUser);
        assertEquals(SUCCESFUL_AGENT_ACTION, userDAO.update(user, nUser));

        User user1 = userDAO.selectPerson("agent3");
        User nUser1 = new User(user1.getUsername(), user1.getName(), user1.getBoundingType(), user1.getDependency());
        nUser1.setAgent(false);
        assertEquals(FAILED_AGENT_ACTION, userDAO.update(user1, nUser1));

//        User user2 = userDAO.selectPerson("agent96");
//        User nUser2 = new User(user2.getUsername(), user2.getName(), user2.getBoundingType(), user2.getDependency());
//        nUser2.setAgent(false);
//        assertEquals(FAILED_AGENT_ACTION, userDAO.update(user2, nUser2));
    }


    @Test
    void ActivateAgent() {

        User user = userDAO.selectPerson("agent3");
        User nUser = new User(user.getUsername(), user.getName(), user.getBoundingType(), user.getDependency());
        nUser.setAgent(true);
        System.out.println(user);
        System.out.println(nUser);
        assertEquals(SUCCESFUL_AGENT_ACTION, userDAO.update(user, nUser));

        User user1 = userDAO.selectPerson("agent3");
        User nUser1 = new User(user1.getUsername(), user1.getName(), user1.getBoundingType(), user1.getDependency());
        nUser1.setAgent(true);
        assertEquals(FAILED_AGENT_ACTION, userDAO.update(user1, nUser1));

//        User user2 = userDAO.selectPerson("agent96");
//        User nUser2 = new User(user2.getUsername(), user2.getName(), user2.getBoundingType(), user2.getDependency());
//        nUser2.setAgent(false);
//        assertEquals(FAILED_AGENT_ACTION, userDAO.update(user2, nUser2));
    }
}
