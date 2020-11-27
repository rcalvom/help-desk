package com.helpdesk.HelpDesk.Controllers;

import com.helpdesk.HelpDesk.DAO.CategoryDAO;
import com.helpdesk.HelpDesk.DAO.DependencyDAO;
import com.helpdesk.HelpDesk.DAO.RequestDAO;
import com.helpdesk.HelpDesk.DAO.UserDAO;
import com.helpdesk.HelpDesk.Forms.*;
import com.helpdesk.HelpDesk.Models.*;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

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

    //Bandeja de entrada
    @GetMapping("/admin/inbox")
    public String inboxRequestsAdminDefault(Model model){
        if(userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])){
            List<Request> requests = (List<Request>) requestDAO.selectByStatus(Request.Status.NO_ASIGNADO);
            model.addAttribute("Requests", requests);
            return "inbox-requests-admin";
        }else{
            return "redirect:/error/403";
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
        return "redirect:/error/403";
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
            return "redirect:/error/403";
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
            return "redirect:/error/403";
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
            return "redirect:/error/403";
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
            return "redirect:/error/403";
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
            return "redirect:/error/403";
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
            return "redirect:/error/403";
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
            return "redirect:/error/403";
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
            return "redirect:/error/403";
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
            return "redirect:/error/403";
        }
    }

    @GetMapping("/admin/info")
    public String infoAdminDefault(Model model) {
        if(userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])) {
            return "info-admin";
        }else{
            return "redirect:/error/403";
        }
    }

    @GetMapping("/admin/reports")
    public String reportsAdminDefault(Model model) {
        if(userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])) {
            ReportForm form = new ReportForm();
            return "reports-admin";
        }else{
            return "redirect:/error/403";
        }
    }


    @GetMapping("/admin/csv")
    public String csvAdminDefault(HttpServletResponse response) throws Exception {
        if(userDAO.selectAdmin().getUsername().equals(((String) (Objects.requireNonNull(((DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAttribute("email")))).split("@")[0])) {
            List<RequestReportForm> reports = new ArrayList<>();
            List<Request> requests = (List<Request>) requestDAO.select();
            for (Request req : requests) {
                RequestReportForm requestReportForm = new RequestReportForm();
                requestReportForm.setId(req.getId());
                requestReportForm.setSpecification(req.getSpecification());
                requestReportForm.setCreationDate(req.formatCreationDate());
                if (req.getEndingDate() != null) requestReportForm.setEndingDate(req.formatEndingDate());
                else requestReportForm.setEndingDate("");
                requestReportForm.setStatus(req.getStatus().name());
                String agNames = "", agentNames = "";
                Set<User> agents = req.getAgents();
                for (User a : agents) {
                    agNames = agNames + a.getName() + ", ";
                }
                if (!agNames.equals("")) agentNames = agNames.substring(0, (agNames.length() - 2));
                requestReportForm.setAgentsNames(agentNames);
                if (req.getInventoryPlate() != null)
                    requestReportForm.setInventoryPlate(req.getInventoryPlate().intValue());
                else requestReportForm.setInventoryPlate(0);
                requestReportForm.setEquipmentNumber(req.getEquipmentNumber());
                requestReportForm.setUserName(req.getUser().getName());
                if (req.getCategory() != null) requestReportForm.setCategory(req.getCategory().getName());
                else requestReportForm.setCategory("");
                if (req.getFeedback() != null) {
                    requestReportForm.setFeedbackSpecification(req.getFeedback().getSpecification());
                    requestReportForm.setFeedbackRating(req.getFeedback().getRating().getName());
                    requestReportForm.setFeedbackDate(req.getFeedback().formatDate());
                } else {
                    requestReportForm.setFeedbackSpecification("");
                    requestReportForm.setFeedbackRating("");
                    requestReportForm.setFeedbackDate("");
                }
                reports.add(requestReportForm);
            }
            String filename = "report.csv";
            response.setContentType("text/csv");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

            StatefulBeanToCsv<RequestReportForm> writer = new StatefulBeanToCsvBuilder<RequestReportForm>(response.getWriter())
                    .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                    .withSeparator(';')
                    .withOrderedResults(true)
                    .build();
            writer.write(reports);
            return null;
        }else{
            return "redirect:/error/403";
        }
    }
}

