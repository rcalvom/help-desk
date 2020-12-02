package com.helpdesk.HelpDesk;

import com.helpdesk.HelpDesk.DAO.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.context.SpringBootTest;
import com.helpdesk.HelpDesk.Controllers.AdminController;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GenerateReportsTest {

    public final boolean SUCCESSFUL_GENERATED_REPORT = true;
    public final boolean FAILED_GENERATED_REPORT = false;
    @Autowired
    private AdminController adminController;

    private HttpServletResponse response;


    @Test
    public void GenerateReport()  throws Exception{

        HttpServletResponse response = new MockHttpServletResponse();
        adminController.writeReportDependency(response, true, true, true, true, true);
        System.out.println(response);
        //Scanner sc = new Scanner(response.getOutputStream().print());
        //System.out.println();
        //response.getWriter().
        //assertEquals(SUCCESSFUL_GENERATED_REPORT, true);
    }
}
