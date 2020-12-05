package com.helpdesk.HelpDesk.Controllers;

import com.helpdesk.HelpDesk.DAO.*;
import com.helpdesk.HelpDesk.Forms.*;
import com.helpdesk.HelpDesk.Models.*;
import com.helpdesk.HelpDesk.ProjectAssistances.CustomMappingStrategy;
import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AdminController {

    private final UserDAO userDAO;
    private final RequestDAO requestDAO;
    private final CategoryDAO categoryDAO;
    private final DependencyDAO dependencyDAO;
    private final BoundingTypeDAO boundingTypeDAO;

    public AdminController(UserDAO userDAO, RequestDAO requestDAO, CategoryDAO categoryDAO, DependencyDAO dependencyDAO, BoundingTypeDAO boundingTypeDAO){
        this.userDAO = userDAO;
        this.requestDAO = requestDAO;
        this.categoryDAO = categoryDAO;
        this.dependencyDAO = dependencyDAO;
        this.boundingTypeDAO = boundingTypeDAO;
    }


    // Bandeja de entrada.
    @GetMapping("/admin/inbox")
    public String inboxRequestsAdminDefault(Model model){
        if(this.userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])){
            List<Request> requests = (List<Request>) this.requestDAO.selectByStatus(Request.Status.NO_ASIGNADO);
            model.addAttribute("Requests", requests);
            return "inbox-requests-admin";
        }else{
            return "redirect:/error/403";
        }
    }

    // Asignar solicitud agente GET
    @GetMapping("/admin/assign-request/{id}")
    public String assignRequestAdminDefault(@PathVariable("id") String id, Model model){
        Request request = this.requestDAO.selectById(id);
        if(this.userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])){
            if(request.getStatus() == Request.Status.NO_ASIGNADO){
                AssignRequestForm req = new AssignRequestForm(request.getId(), request.formatCreationDate(), request.getUser().getUsername(), request.getSpecification(), request.getInventoryPlate(), request.getEquipmentNumber());
                model.addAttribute("assignRequest", req);
                List<User> agt = (List<User>) this.userDAO.selectAgent();
                model.addAttribute("agents", agt);
                List<Category> ctg = (List<Category>) this.categoryDAO.select();
                model.addAttribute("category", ctg);
                return "assign-request-admin";
            }
        }
        return "redirect:/error/403";
    }

    // Asignar solicitud agente POST
    @PostMapping("/admin/assign-request/{id}")
    public String assignRequestAdminPost(@PathVariable("id") String id, @ModelAttribute AssignRequestForm form){
        if(this.userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])) {
            Request request = this.requestDAO.selectById(id);
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
                newRequest.getAgents().add(this.userDAO.selectAgent(username));
            }
            newRequest.setCategory(this.categoryDAO.select(form.getCategory()));
            this.requestDAO.update(request, newRequest);
            return "redirect:/admin/inbox";
        }else{
            return "redirect:/error/403";
        }
    }

    // Solicitudes del sistema.
    @GetMapping("/admin/requests")
    public String requestsAdminDefault(Model model){
        if(this.userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])) {
            List<Request> requests = (List<Request>) this.requestDAO.select();
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
            return "redirect:/error/403";
        }
    }

    // Detalles de la solicitud administrador.
    @GetMapping("/admin/details/{id}")
    public String requestDetailsAdminDefault(@PathVariable("id") String id, Model model){
        if(this.userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])) {
            Request RequestDetail = this.requestDAO.selectById(id);
            model.addAttribute("requestDetail", RequestDetail);
            return "request-details-admin";
        }else{
            return "redirect:/error/403";
        }
    }

    // Gestionar categorias GET
    @GetMapping("/admin/categories")
    public String categoryManagementAdminDefault(Model model){
        if(this.userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])) {
            List<Category> categories = (List<Category>) this.categoryDAO.selectActiveCategories();
            model.addAttribute("categories", categories);
            model.addAttribute("newCategory", new CategoryForm());
            return "category-management-admin";
        }else{
            return "redirect:/error/403";
        }
    }

    // Gestionar categorias POST
    @PostMapping("/admin/categories")
    public String categoryManagementAdminPost(@RequestParam(value = "category", required = false) String category, @ModelAttribute CategoryForm form){
        if(this.userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])) {
            if (category != null) {
                Category cat = this.categoryDAO.select(category);
                Category newCat = new Category();
                newCat.setName(cat.getName());
                newCat.setActive(false);
                this.categoryDAO.update(cat, newCat);
            } else {
                Category newCat = new Category(form.getName());
                if (this.categoryDAO.select(form.getName()) == null) {
                    this.categoryDAO.insert_update(newCat);
                } else {
                    Category cat = this.categoryDAO.select(form.getName());
                    this.categoryDAO.update(cat, newCat);
                }
            }
            return "redirect:/admin/categories";
        }else{
            return "redirect:/error/403";
        }
    }

    // Gestionar agentes GET
    @GetMapping("/admin/agents")
    public String agentManagementAdminDefault(Model model){
        if(this.userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])) {
            List<User> agents = (List<User>) this.userDAO.selectAgent();
            model.addAttribute("agents", agents);
            return "agent-management-admin";
        }else{
            return "redirect:/error/403";
        }
    }

    // Gestionar agentes POST
    @PostMapping("/admin/agents")
    public String agentManagementAdminPost(@RequestParam(value = "username", required = false) String username){
        if(this.userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])) {
            User agent = this.userDAO.selectAgent(username);
            User newAgent = new User();
            newAgent.setUsername(agent.getUsername());
            newAgent.setName(agent.getName());
            newAgent.setAgent(false);
            newAgent.setAdministrator(agent.isAdministrator());
            newAgent.setBoundingType(agent.getBoundingType());
            newAgent.setDependency(agent.getDependency());
            newAgent.setAssignedRequests(agent.getAssignedRequests());
            this.userDAO.update(agent, newAgent);
            return "redirect:/admin/agents";
        }else{
            return "redirect:/error/403";
        }
    }

    // Añadir agente.
    @GetMapping("admin/assign-agent")
    public String agentAssignAdminDefault(Model model){
        if(this.userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])) {
            List<User> users = (List<User>) this.userDAO.selectUser();
            model.addAttribute("users", users);
            return "agent-assign-admin";
        }else{
            return "redirect:/error/403";
        }
    }

    // Gestionar agentes POST
    @PostMapping("/admin/assign-agent")
    public String agentAssignAdminPost(@RequestParam(value = "username", required = false) String username){
        if(this.userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])) {
            User agent = this.userDAO.selectUser(username);
            User newAgent = new User();
            newAgent.setUsername(agent.getUsername());
            newAgent.setName(agent.getName());
            newAgent.setAgent(true);
            newAgent.setAdministrator(agent.isAdministrator());
            newAgent.setBoundingType(agent.getBoundingType());
            newAgent.setDependency(agent.getDependency());
            newAgent.setAssignedRequests(agent.getAssignedRequests());
            newAgent.setLocation(agent.getLocation());
            newAgent.setPhone(agent.getPhone());
            newAgent.setPhoneExtension(agent.getPhoneExtension());
            this.userDAO.update(agent, newAgent);
            return "redirect:/admin/agents";
        }else{
            return "redirect:/error/403";
        }
    }

    // Gestionar agentes GET
    @GetMapping("/admin/info")
    public String infoAdminDefault(){
        if(this.userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])) {
            return "info-admin";
        }else{
            return "redirect:/error/403";
        }
    }

    // Reporte de todas las solicitudes.
    @GetMapping("/admin/csv")
    public String csvAdminDefault(HttpServletResponse response) throws Exception {
        if(this.userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])) {
            String filename = "report " + this.formatDate(Calendar.getInstance().getTime()) + ".csv";
            List<RequestReportForm> reports = new ArrayList<>();
            List<Request> requests = (List<Request>) this.requestDAO.select();
            for(Request req : requests){
                reports.add(new RequestReportForm(req));
            }
            response.setContentType("text/csv");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ filename +"\"");
            final CustomMappingStrategy<RequestReportForm> mappingStrategy = new CustomMappingStrategy<>(null);
            mappingStrategy.setType(RequestReportForm.class);
            final StatefulBeanToCsv<RequestReportForm> beanToCsv = new StatefulBeanToCsvBuilder<RequestReportForm>(response.getWriter())
                    .withMappingStrategy(mappingStrategy)
                    .withSeparator(';')
                    .build();
            beanToCsv.write(reports);
            response.getWriter().close();
            return null;
        }else{
            return "redirect:/error/403";
        }
    }

    // Generar Reportes GET
    @GetMapping("/admin/reports")
    public String reportsAdminDefault(Model model){
        if(this.userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])) {
            model.addAttribute("reportForm", new ReportForm());
            model.addAttribute("selectTypes", Arrays.asList(ReportForm.SelectType.values()));
            return "reports-admin";
        }else{
            return "redirect:/error/403";
        }
    }

    // Generar Reportes POST
    @PostMapping("/admin/reports")
    public String reportsAdminPost(@ModelAttribute ReportForm form, HttpServletResponse response) throws Exception {
        if(this.userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])) {
            switch (form.getSelectType()){
                case Dependencia:
                    this.writeReportDependency(response, false, form.getEquipment(), form.getRequest(), form.getRating(), form.getEfficacy(), form.getDuration());
                    break;
                case Vinculación:
                    this.writeReportBounding(response, false, form.getEquipment(), form.getRequest(), form.getRating(), form.getEfficacy(), form.getDuration());
                    break;
                case Agente:
                    this.writeReportAgent(response, false, form.getEquipment(), form.getRequest(), form.getRating(), form.getEfficacy(), form.getDuration());
                    break;
                case Categoría:
                    this.writeReportCategory(response, false, form.getEquipment(), form.getRequest(), form.getRating(), form.getEfficacy(), form.getDuration());
                    break;
            }
            return null;
        }else{
            return "redirect:/error/403";
        }
    }


    // Reporte por Dependencia
    public void writeReportDependency(HttpServletResponse response,  boolean localWriter, boolean... toShow) throws Exception {
        String filename = "report " + this.formatDate(Calendar.getInstance().getTime()) + ".csv";
        List<DependencyReportForm> reports = new ArrayList<>();
        List<Dependency> dependencies = (List<Dependency>) this.dependencyDAO.select();
        for(Dependency dependency : dependencies){
            reports.add(new DependencyReportForm(dependency, toShow));
        }
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

        final CustomMappingStrategy<DependencyReportForm> mappingStrategy = new CustomMappingStrategy<>(toShow);
        mappingStrategy.setType(DependencyReportForm.class);
        Writer fl = localWriter ? new FileWriter("report.csv") : response.getWriter();
        final StatefulBeanToCsv<DependencyReportForm> writer = new StatefulBeanToCsvBuilder<DependencyReportForm>(fl)
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withSeparator(';')
                .withOrderedResults(true)
                .withMappingStrategy(mappingStrategy)
                .build();
        writer.write(reports);
        fl.close();
    }

    // Reporte por vinculación
    public void writeReportBounding(HttpServletResponse response, boolean localWriter, boolean... toShow) throws Exception {
        String filename = "report " + this.formatDate(Calendar.getInstance().getTime()) + ".csv";
        List<BoundingTypeReportForm> reports = new ArrayList<>();
        List<BoundingType> boundingTypes = (List<BoundingType>) this.boundingTypeDAO.select();
        for(BoundingType boundingType : boundingTypes){
            reports.add(new BoundingTypeReportForm(boundingType, toShow));
        }
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
        Writer fl = localWriter ? new FileWriter("report.csv") : response.getWriter();
        final CustomMappingStrategy<BoundingTypeReportForm> mappingStrategy = new CustomMappingStrategy<>(toShow);
        mappingStrategy.setType(BoundingTypeReportForm.class);
        StatefulBeanToCsv<BoundingTypeReportForm> writer = new StatefulBeanToCsvBuilder<BoundingTypeReportForm>(fl)
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withMappingStrategy(mappingStrategy)
                .withSeparator(';')
                .withOrderedResults(true)
                .build();
        writer.write(reports);
        fl.close();
    }

    // Reporte por Agente
    public void writeReportAgent(HttpServletResponse response, boolean localWriter, boolean... toShow) throws Exception {
        String filename = "report " + this.formatDate(Calendar.getInstance().getTime()) + ".csv";
        List<AgentReportForm> reports = new ArrayList<>();
        List<User> agents = (List<User>) this.userDAO.selectAgent();
        for(User agent : agents){
            reports.add(new AgentReportForm(agent, toShow));
        }
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
        Writer fl = localWriter ? new FileWriter("report.csv") : response.getWriter();
        final CustomMappingStrategy<AgentReportForm> mappingStrategy = new CustomMappingStrategy<>(toShow);
        mappingStrategy.setType(AgentReportForm.class);
        StatefulBeanToCsv<AgentReportForm> writer = new StatefulBeanToCsvBuilder<AgentReportForm>(fl)
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withSeparator(';')
                .withMappingStrategy(mappingStrategy)
                .withOrderedResults(true)
                .build();
        writer.write(reports);
        fl.close();
    }

    // Reporte por Categoria
    public void writeReportCategory(HttpServletResponse response, boolean localWriter, boolean... toShow) throws Exception {
        String filename = "report " + this.formatDate(Calendar.getInstance().getTime()) + ".csv";
        List<CategoryReportForm> reports = new ArrayList<>();
        List<Category> categories = (List<Category>) this.categoryDAO.select();
        for(Category category : categories){
            reports.add(new CategoryReportForm(category, toShow));
        }
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
        Writer fl = localWriter ? new FileWriter("report.csv") : response.getWriter();
        final CustomMappingStrategy<CategoryReportForm> mappingStrategy = new CustomMappingStrategy<>(toShow);
        mappingStrategy.setType(CategoryReportForm.class);
        StatefulBeanToCsv<CategoryReportForm> writer = new StatefulBeanToCsvBuilder<CategoryReportForm>(fl)
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withSeparator(';')
                .withMappingStrategy(mappingStrategy)
                .withOrderedResults(true)
                .build();
        writer.write(reports);
        fl.close();
    }

    private String formatDate(Date date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return date != null ? dateFormat.format(date.getTime()) : null;
    }
}

