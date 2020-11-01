package com.helpdesk.HelpDesk;

import com.helpdesk.HelpDesk.DAO.CategoryDAO;
import com.helpdesk.HelpDesk.DAO.RequestDAO;
import com.helpdesk.HelpDesk.DAO.UserDAO;
import com.helpdesk.HelpDesk.Models.Category;
import com.helpdesk.HelpDesk.Models.Request;
import com.helpdesk.HelpDesk.Models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CreateRequestTest {

    public final boolean SUCCESSFUL_REQUEST_CREATION = true;
    public final boolean FAILED_REQUEST_CREATION = false;

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RequestDAO requestDAO;
    @Autowired
    private CategoryDAO categoryDAO;

    @Test
    public void UserRequestFailed() {
        User user = userDAO.selectPerson("user");
        Request request = new Request("", user);
        assertEquals(FAILED_REQUEST_CREATION, requestDAO.insert(request));

        User user2 = userDAO.selectPerson("pablo");
        Request request2 = new Request("No sé que pasó", user2);
        assertEquals(FAILED_REQUEST_CREATION, requestDAO.insert(request2));
    }

    @Test
    public void UserRequestSuccess() {
        User user = userDAO.selectPerson("user");
        Request request = new Request("Concepto de baja de equipo", user);
        assertEquals(SUCCESSFUL_REQUEST_CREATION, requestDAO.insert(request));

        User user2 = userDAO.selectPerson("agent1");
        Request request2 = new Request("Cotización", user2);
        assertEquals(SUCCESSFUL_REQUEST_CREATION, requestDAO.insert(request2));
    }

    @Test
    public void AdminRequestFailed(){
        Request request;
        try {
            request = requestDAO.selectByStatus(Request.Status.NO_ASIGNADO).iterator().next();
            User agent = userDAO.selectAgent("agent1");
            request.setAgents(agent);
            request.setEquipmentNumber(3);
            request.setInventoryPlate(123);
            Category category = categoryDAO.select("Baja de equipos");
            request.setCategory(category);
        }catch (Exception e){
            request = null;
        }
        assertEquals(FAILED_REQUEST_CREATION, requestDAO.insert(request));

        Request request2;
        try {
            request2 = requestDAO.selectByStatus(Request.Status.NO_ASIGNADO).iterator().next();
            Set<User> agents = Set.of(userDAO.selectAgent("agent3"), userDAO.selectAgent("agent2"));
            request2.setAgents(agents);
            request2.setStatus(Request.Status.ACTIVO);
            request2.setEquipmentNumber(5);
            request2.setInventoryPlate(1234);
            Category category2 = categoryDAO.select("limpiesa");
            System.out.println(category2);
            request2.setCategory(category2);

        }catch (Exception e){
            request2 = null;
        }
        assertEquals(FAILED_REQUEST_CREATION, requestDAO.insert(request2));
    }

    @Test
    public void AdminRequestSuccess(){
        Request request;
        try {
            request = requestDAO.selectByStatus(Request.Status.NO_ASIGNADO).iterator().next();
            User agent = userDAO.selectAgent("agent1");
            request.setAgents(agent);
            request.setStatus(Request.Status.ACTIVO);
            request.setEquipmentNumber(3);
            Category category = categoryDAO.select("Baja de equipos");
            request.setCategory(category);
        }catch (Exception e){
            request = null;
        }
        assertEquals(SUCCESSFUL_REQUEST_CREATION, requestDAO.insert(request));

        Request request2;
        try {
            request2 = requestDAO.selectByStatus(Request.Status.NO_ASIGNADO).iterator().next();
            Set<User> agents = Set.of(userDAO.selectAgent("agent3"), userDAO.selectAgent("agent2"));
            request2.setAgents(agents);
            request2.setStatus(Request.Status.ACTIVO);
            request2.setEquipmentNumber(5);
            request2.setInventoryPlate(1234);
            Category category2 = categoryDAO.select("Limpieza");
            request2.setCategory(category2);
        }catch (Exception e){
            request2 = null;
        }
        assertEquals(SUCCESSFUL_REQUEST_CREATION, requestDAO.insert(request2));
    }

}
