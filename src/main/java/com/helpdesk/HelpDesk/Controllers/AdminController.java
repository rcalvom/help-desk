package com.helpdesk.HelpDesk.Controllers;

import com.helpdesk.HelpDesk.DAO.*;
import com.helpdesk.HelpDesk.Forms.*;
import com.helpdesk.HelpDesk.Models.*;
import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import org.apache.commons.collections4.comparators.FixedOrderComparator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalTime;
import java.util.*;

import static org.hibernate.mapping.MetadataSource.ANNOTATIONS;

//class CustomMappingStrategy<T> extends ColumnPositionMappingStrategy<T> {
////    @Override
//    public String[] generateHeader(boolean toShow[]) {
//        int countTrue = 0;
//        for(int i = 0; i < toShow.length; ++i){
//            if(toShow[i]) countTrue++;
//        }
//        BeanField beanField;
//        String[] header = new String[countTrue+1];
//        beanField = findField(0);
//        String columnHeaderName = extractHeaderName(beanField);
//        header[0] = columnHeaderName;
//        for (int i = 0; i < toShow.length; i++) {
//            if(toShow[i]){
//                beanField = findField(i+1);
//                columnHeaderName = extractHeaderName(beanField);
//                header[i+1] = columnHeaderName;
//            }
//        }
//        return header;
//    }
//
//    private String extractHeaderName(final BeanField beanField) {
//        if (beanField == null || beanField.getField() == null || beanField.getField().getDeclaredAnnotationsByType(CsvBindByName.class).length == 0) {
//            return StringUtils.EMPTY;
//        }
//        final CsvBindByName bindByNameAnnotation = beanField.getField().getDeclaredAnnotationsByType(CsvBindByName.class)[0];
//        System.out.println("sillegó hasta aquí"  + bindByNameAnnotation.column());
//        return bindByNameAnnotation.column();
//    }
//}

@Controller
public class AdminController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RequestDAO requestDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private DependencyDAO dependencyDAO;

    @Autowired
    private BoundingTypeDAO boundingTypeDAO;

    //Bandeja de entrada
    @GetMapping("/admin/inbox")
    public String inboxRequestsAdminDefault(Model model){
        if(userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])){
            List<Request> requests = (List<Request>) requestDAO.selectByStatus(Request.Status.NO_ASIGNADO);
            model.addAttribute("Requests", requests);
            return "inbox-requests-admin";
        }else{
            return "redirect:/error";
        }
    }

    //Asignar solicitud agente
    @GetMapping("/admin/assign-request/{id}")
    public String assignRequestAdminDefault(@PathVariable("id") String id, Model model){
        Request request = requestDAO.selectById(id);
        if(userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])){
            if(request.getStatus() == Request.Status.NO_ASIGNADO){
                AssignRequestForm req = new AssignRequestForm(request.getId(), request.formatCreationDate(), request.getUser().getUsername(), request.getSpecification(), request.getInventoryPlate(), request.getEquipmentNumber());
                model.addAttribute("assignRequest", req);
                List<User> agt = (List<User>) userDAO.selectAgent();
                model.addAttribute("agents", agt);
                List<Category> ctg = (List<Category>) categoryDAO.select();
                model.addAttribute("category", ctg);
                return "assign-request-admin";
            }
        }
        return "redirect:/error";
    }

    @PostMapping("/admin/assign-request/{id}")
    public String assignRequestAdminPost(@PathVariable("id") String id, @ModelAttribute AssignRequestForm form){
        if(userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])) {
            Request request = requestDAO.selectById(id);
            Request newRequest = new Request();
            newRequest.setId(request.getId());
            newRequest.setSpecification(request.getSpecification());
            newRequest.setCreationDate(request.getCreationDate());
            newRequest.setStatus(Request.Status.ACTIVO);
            newRequest.setInventoryPlate(form.getInventoryPlate());
            newRequest.setEquipmentNumber(form.getEquipmentNumber());
            newRequest.setUser(request.getUser());
            newRequest.setAgents(new HashSet<>());
            for (String username : form.getAgentUsername()) {
                newRequest.getAgents().add(userDAO.selectAgent(username));
            }
            newRequest.setCategory(categoryDAO.select(form.getCategory()));
            requestDAO.update(request, newRequest);
            return "redirect:/admin/inbox";
        }else{
            return "redirect:/error";
        }
    }

    // Solicitudes del sistema
    @GetMapping("/admin/requests")
    public String requestsAdminDefault(Model model){
        if(userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])) {
            List<Request> requests = (List<Request>) requestDAO.select();
            List<Request> requestsAc = new ArrayList<>();
            List<Request> requestsCl = new ArrayList<>();
            for(Request req : requests){
                if(req.getStatus() == Request.Status.ACTIVO || req.getStatus() == Request.Status.NO_ASIGNADO){
                    requestsAc.add(req);
                }
                else{
                    requestsCl.add(req);
                }
            }
            model.addAttribute("RequestsAc", requestsAc);
            model.addAttribute("RequestsCl", requestsCl);
            return "requests-admin";
        }else{
            return "redirect:/error";
        }
    }

    //Detalles de la solicitud administrador
    @GetMapping("/admin/details/{id}")
    public String requestDetailsAdminDefault(@PathVariable("id") String id, Model model){
        if(userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])) {
            Request RequestDetail = requestDAO.selectById(id);
            model.addAttribute("requestDetail", RequestDetail);
            return "request-details-admin";
        }else{
            return "redirect:/error";
        }
    }

    //Gestionar categorias
    @GetMapping("/admin/categories")
    public String categoryManagementAdminDefault(Model model){
        if(userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])) {
            List<Category> categories = (List<Category>) categoryDAO.selectActiveCategories();
            model.addAttribute("categories", categories);
            model.addAttribute("newCategory", new CategoryForm());
            return "category-management-admin";
        }else{
            return "redirect:/error";
        }
    }

    @PostMapping("/admin/categories")
    public String categoryManagementAdminPost(@RequestParam(value = "category", required = false) String category, @ModelAttribute CategoryForm form){
        if(userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])) {
            if (category != null) {
                Category cat = categoryDAO.select(category);
                Category newCat = new Category();
                newCat.setName(cat.getName());
                newCat.setActive(false);
                categoryDAO.update(cat, newCat);
            } else {
                Category newCat = new Category(form.getName());
                if (categoryDAO.select(form.getName()) == null) {
                    categoryDAO.insert_update(newCat);
                } else {
                    Category cat = categoryDAO.select(form.getName());
                    categoryDAO.update(cat, newCat);
                }
            }
            return "redirect:/admin/categories";
        }else{
            return "redirect:/error";
        }
    }

    //Gestionar agentes
    @GetMapping("/admin/agents")
    public String agentManagementAdminDefault(Model model){
        if(userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])) {
            List<User> agents = (List<User>) userDAO.selectAgent();
            model.addAttribute("agents", agents);
            return "agent-management-admin";
        }else{
            return "redirect:/error";
        }
    }

    @PostMapping("/admin/agents")
    public String agentManagementAdminPost(@RequestParam(value = "username", required = false) String username){
        if(userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])) {
            User agent = userDAO.selectAgent(username);
            User newAgent = new User();
            newAgent.setUsername(agent.getUsername());
            newAgent.setName(agent.getName());
            newAgent.setAgent(false);
            newAgent.setAdministrator(agent.isAdministrator());
            newAgent.setBoundingType(agent.getBoundingType());
            newAgent.setDependency(agent.getDependency());
            newAgent.setAssignedRequests(agent.getAssignedRequests());
            userDAO.update(agent, newAgent);
            return "redirect:/admin/agents";
        }else{
            return "redirect:/error";
        }
    }

    //Añadir agente
    @GetMapping("admin/assign-agent")
    public String agentAssignAdminDefault(Model model){
        if(userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])) {
            List<User> users = (List<User>) userDAO.selectUser();
            model.addAttribute("users", users);
            return "agent-assign-admin";
        }else{
            return "redirect:/error";
        }
    }

    @PostMapping("/admin/assign-agent")
    public String agentAssignAdminPost(@RequestParam(value = "username", required = false) String username){
        if(userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])) {
            User agent = userDAO.selectUser(username);
            User newAgent = new User();
            newAgent.setUsername(agent.getUsername());
            newAgent.setName(agent.getName());
            newAgent.setAgent(true);
            newAgent.setAdministrator(agent.isAdministrator());
            newAgent.setBoundingType(agent.getBoundingType());
            newAgent.setDependency(agent.getDependency());
            newAgent.setAssignedRequests(agent.getAssignedRequests());
            userDAO.update(agent, newAgent);
            return "redirect:/admin/agents";
        }else{
            return "redirect:/error";
        }
    }

    @GetMapping(value = "/reports")
    public void reportsAdminDefault(HttpServletResponse response) throws Exception {
        this.WriteReport(response);
    }

    // Reporte de todas las solicitudes
    /*private void WriteReport(HttpServletResponse response) throws Exception{
        String filename = "report.csv";
        List<RequestReportForm> reports = new ArrayList<>();
        List<Request> requests = (List<Request>) requestDAO.select();
        for(Request req : requests){
            reports.add(new RequestReportForm(req));
        }

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ filename +"\"");

        final CustomMappingStrategy<RequestReportForm> mappingStrategy = new CustomMappingStrategy<>();
        mappingStrategy.setType(RequestReportForm.class);

        final StatefulBeanToCsv<RequestReportForm> beanToCsv = new StatefulBeanToCsvBuilder<RequestReportForm>(response.getWriter())
                .withMappingStrategy(mappingStrategy)
                .build();
        beanToCsv.write(reports);
        response.getWriter().close();
    }/**/

    // Reporte por Dependencia
    /*private void WriteReport(HttpServletResponse response) throws Exception {
        List<DependencyReportForm> reports = new ArrayList<>();
        List<Dependency> dependencies = (List<Dependency>) dependencyDAO.select();
        boolean toShow[] = {true, false, true, false, true, false, true};
        for(Dependency dependency : dependencies){
            reports.add(new DependencyReportForm(dependency, toShow));
        }
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\" report.csv\"");

        final CustomMappingStrategy<DependencyReportForm> mappingStrategy = new CustomMappingStrategy<>(toShow);
        mappingStrategy.setType(DependencyReportForm.class);

        final StatefulBeanToCsv<DependencyReportForm> writer = new StatefulBeanToCsvBuilder<DependencyReportForm>(response.getWriter())
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withSeparator(';')
                .withOrderedResults(true)
                .withMappingStrategy(mappingStrategy)
                .build();
        writer.write(reports);
    }/**/

//    boolean toShow[] = {true, true, true, true, true, true, true};
    // Reporte por vinculación
    /*private void WriteReport(HttpServletResponse response) throws Exception {
        List<BoundingTypeReportForm> reports = new ArrayList<>();
        List<BoundingType> boundingTypes = (List<BoundingType>) boundingTypeDAO.select();
        boolean toShow[] = {true, false, true, false, true, false, true};
//        boolean toShow[] = {true, true, true, true, true, true, true};

        for(BoundingType boundingType : boundingTypes){
            reports.add(new BoundingTypeReportForm(boundingType, toShow));
        }
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\" report.csv\"");

        final CustomMappingStrategy<BoundingTypeReportForm> mappingStrategy = new CustomMappingStrategy<>(toShow);
        mappingStrategy.setType(BoundingTypeReportForm.class);
        StatefulBeanToCsv<BoundingTypeReportForm> writer = new StatefulBeanToCsvBuilder<BoundingTypeReportForm>(response.getWriter())
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withMappingStrategy(mappingStrategy)
                .withSeparator(';')
                .withOrderedResults(true)
                .build();
        writer.write(reports);
    }/**/

    // Reporte por Category
    /*private void WriteReport(HttpServletResponse response) throws Exception {
        List<CategoryReportForm> reports = new ArrayList<>();
        List<Category> categories = (List<Category>) categoryDAO.select();
        boolean toShow[] = {true, false, true, false, true, false, true};
//        boolean toShow[] = {true, true, true, true, true, true, true};
        for(Category category : categories){
            reports.add(new CategoryReportForm(category, toShow));
        }
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\" report.csv\"");
        final CustomMappingStrategy<CategoryReportForm> mappingStrategy = new CustomMappingStrategy<>(toShow);
        mappingStrategy.setType(CategoryReportForm.class);
        StatefulBeanToCsv<CategoryReportForm> writer = new StatefulBeanToCsvBuilder<CategoryReportForm>(response.getWriter())
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withSeparator(';')
                .withMappingStrategy(mappingStrategy)
                .withOrderedResults(true)
                .build();
        writer.write(reports);
    }/**/

    // Reporte por Agente
    private void WriteReport(HttpServletResponse response) throws Exception {
        List<AgentReportForm> reports = new ArrayList<>();
        List<User> agents = (List<User>) userDAO.selectAgent();
        boolean toShow[] = {true, false, true, false, true, false, true};
//        boolean toShow[] = {true, true, true, true, true, true, true};
        for(User agent : agents){
            reports.add(new AgentReportForm(agent, toShow));
        }
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\" report.csv\"");
        final CustomMappingStrategy<AgentReportForm> mappingStrategy = new CustomMappingStrategy<>(toShow);
        mappingStrategy.setType(AgentReportForm.class);
        StatefulBeanToCsv<AgentReportForm> writer = new StatefulBeanToCsvBuilder<AgentReportForm>(response.getWriter())
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withSeparator(';')
                .withMappingStrategy(mappingStrategy)
                .withOrderedResults(true)
                .build();
        writer.write(reports);
    }/**/


}

