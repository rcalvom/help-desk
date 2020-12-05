package com.helpdesk.HelpDesk;

import com.helpdesk.HelpDesk.DAO.*;
import com.helpdesk.HelpDesk.Forms.AgentReportForm;
import com.helpdesk.HelpDesk.Forms.BoundingTypeReportForm;
import com.helpdesk.HelpDesk.Forms.CategoryReportForm;
import com.helpdesk.HelpDesk.Forms.DependencyReportForm;
import com.helpdesk.HelpDesk.Models.BoundingType;
import com.helpdesk.HelpDesk.Models.Category;
import com.helpdesk.HelpDesk.Models.Dependency;
import com.helpdesk.HelpDesk.Models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.helpdesk.HelpDesk.Controllers.AdminController;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GenerateReportsTest {

    public final boolean SUCCESSFUL_GENERATED_REPORT = true;
    public final boolean FAILED_GENERATED_REPORT = false;

    @Autowired
    private AdminController adminController;
    @Autowired
    private DependencyDAO dependencyDAO;
    @Autowired
    private BoundingTypeDAO boundingTypeDAO;
    @Autowired
    private CategoryDAO categoryDAO;
    @Autowired
    private UserDAO userDAO;

    @Test
    public void GenerateReport()  throws Exception{
        //Dependency Report
        HttpServletResponse response = new MockHttpServletResponse();
        boolean[] toShow = {true, true, false, true, false};
        adminController.writeReportDependency(response, true, toShow);

        Scanner sc = new Scanner(new File("report.csv"));
        String[] header = sc.nextLine().split(";");
        boolean difHeader = false;
        int counterHeaders = 1;
        for(int i = 0; i < DependencyReportForm.columns.length; i++){
            if(toShow[i]){
                System.out.println(header[counterHeaders]);
                String current = DependencyReportForm.columns[i].substring(1, DependencyReportForm.columns[i].length()-1);
                if(!header[counterHeaders++].equals(DependencyReportForm.columns[i])){
                    difHeader = true;
                }
            }
        }
        boolean difIndex = false;
        Iterable<Dependency> dependencies = dependencyDAO.select();
        for(Dependency dependency : dependencies){
            if(!sc.nextLine().split(";")[0].equals(dependency.getName())){
                difIndex = true;
            }
        }
        new File("report.csv").delete();
        assertEquals(SUCCESSFUL_GENERATED_REPORT, difIndex && difHeader);

        //Bounding Report
        response = new MockHttpServletResponse();
        toShow = new boolean[]{false, true, false, true, false};
        adminController.writeReportBounding(response, true, toShow);

        sc = new Scanner(new File("report.csv"));
        header = sc.nextLine().split(";");
        difHeader = false;
        counterHeaders = 1;
        for(int i = 0; i < BoundingTypeReportForm.columnas.length; i++){
            if(toShow[i]){
                System.out.println(header[counterHeaders]);
                String current = BoundingTypeReportForm.columnas[i].substring(1, BoundingTypeReportForm.columnas[i].length()-1);
                if(!header[counterHeaders++].equals(BoundingTypeReportForm.columnas[i])){
                    difHeader = true;
                }
            }
        }
        difIndex = false;
        Iterable<BoundingType> boundingTypes = boundingTypeDAO.select();
        for(BoundingType boundingType : boundingTypes){
            if(!sc.nextLine().split(";")[0].equals(boundingType.getName())){
                difIndex = true;
            }
        }
        new File("report.csv").delete();
        assertEquals(SUCCESSFUL_GENERATED_REPORT, difIndex && difHeader);

        //Category Report
        response = new MockHttpServletResponse();
        toShow = new boolean[]{false, false, false, true, false};
        adminController.writeReportCategory(response, true, toShow);

        sc = new Scanner(new File("report.csv"));
        header = sc.nextLine().split(";");
        difHeader = false;
        counterHeaders = 1;
        for(int i = 0; i < CategoryReportForm.columns.length; i++){
            if(toShow[i]){
                System.out.println(header[counterHeaders]);
                String current = CategoryReportForm.columns[i].substring(1, CategoryReportForm.columns[i].length()-1);
                if(!header[counterHeaders++].equals(CategoryReportForm.columns[i])){
                    difHeader = true;
                }
            }
        }
        difIndex = false;
        Iterable<Category> categories = categoryDAO.select();
        for(Category category : categories){
            if(!sc.nextLine().split(";")[0].equals(category.getName())){
                difIndex = true;
            }
        }
        new File("report.csv").delete();
        assertEquals(SUCCESSFUL_GENERATED_REPORT, difIndex && difHeader);

        //Agent report
        response = new MockHttpServletResponse();
        toShow = new boolean[]{false, false, true, false, false};
        adminController.writeReportCategory(response, true, toShow);

        sc = new Scanner(new File("report.csv"));
        header = sc.nextLine().split(";");
        difHeader = false;
        counterHeaders = 1;
        for(int i = 0; i < AgentReportForm.columnas.length; i++){
            if(toShow[i]){
                System.out.println(header[counterHeaders]);
                String current = AgentReportForm.columnas[i].substring(1, AgentReportForm.columnas[i].length()-1);
                if(!header[counterHeaders++].equals(AgentReportForm.columnas[i])){
                    difHeader = true;
                }
            }
        }
        difIndex = false;
        Iterable<User> users = userDAO.selectAgent();
        for(User user : users){
            if(!sc.nextLine().split(";")[0].equals(user.getName())){
                difIndex = true;
            }
        }
        new File("report.csv").delete();
        assertEquals(SUCCESSFUL_GENERATED_REPORT, difIndex && difHeader);

    }
}
