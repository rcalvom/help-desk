package com.helpdesk.HelpDesk.Forms;

import com.helpdesk.HelpDesk.Models.Request;
import com.helpdesk.HelpDesk.Models.User;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import java.util.Set;

public class RequestReportForm {

    @CsvBindByName(column = "ID")
    @CsvBindByPosition(position = 0)
    private String id;

    @CsvBindByName(column = "Especificación")
    @CsvBindByPosition(position = 1)
    private String specification;

    @CsvBindByName(column = "Fecha de creación")
    @CsvBindByPosition(position = 2)
    private String creationDate;

    @CsvBindByName(column = "Fecha de cierre")
    @CsvBindByPosition(position = 3)
    private String endingDate;

    @CsvBindByName(column = "Estado")
    @CsvBindByPosition(position = 4)
    private String status;

    @CsvBindByName(column = "Placa de inventario")
    @CsvBindByPosition(position = 5)
    private int inventoryPlate;

    @CsvBindByName(column = "Número de equipo")
    @CsvBindByPosition(position = 6)
    private int equipmentNumber;

    @CsvBindByName(column = "Usuario")
    @CsvBindByPosition(position = 7)
    private String userName;

    @CsvBindByName(column = "Agente")
    @CsvBindByPosition(position = 8)
    private String agentsNames;

    @CsvBindByName(column = "Categoría")
    @CsvBindByPosition(position = 9)
    private String category;

    @CsvBindByName(column = "Retroalimentación")
    @CsvBindByPosition(position = 10)
    private String feedbackSpecification;

    @CsvBindByName(column = "Calificación")
    @CsvBindByPosition(position = 11)
    private Integer feedbackRating;

    @CsvBindByName(column = "¿Fue solucionada?")
    @CsvBindByPosition(position = 12)
    private boolean successful;

    @CsvBindByName(column = "Fecha de calificación")
    @CsvBindByPosition(position = 13)
    private String feedbackDate;

    public RequestReportForm(Request request){
        this.id = request.getId();
        this.specification = request.getSpecification();
        this.creationDate = request.formatCreationDate();
        this.endingDate = request.getEndingDate() != null ? request.formatEndingDate() : "";
        this.status = request.getStatus().name();

        StringBuilder agNames = new StringBuilder();
        String agentNames = "";
        Set<User> agents = request.getAgents();
        for (User a : agents) {
            agNames.append(a.getName()).append(", ");
        }
        if(!agNames.toString().equals("")) {
            agentNames = agNames.substring(0, agNames.length() - 2);
        }

        this.agentsNames = agentNames;
        this.inventoryPlate = request.getInventoryPlate() != null  ? request.getInventoryPlate() : 0;
        this.equipmentNumber = request.getEquipmentNumber();
        this.userName = request.getUser().getName();
        this.category = request.getCategory() != null ? request.getCategory().getName() : "";
        this.feedbackSpecification = request.getFeedback() != null ? request.getFeedback().getSpecification() : "";
//        this.feedbackRating = request.getFeedback() != null ? request.getFeedback().getRating().getName() : 0;
//        this.successful = request.getFeedback().getSuccessful;
        this.feedbackDate = request.getFeedback() != null ? request.getFeedback().formatDate() : "";
    }

    public RequestReportForm() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(String endingDate) {
        this.endingDate = endingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getInventoryPlate() {
        return inventoryPlate;
    }

    public void setInventoryPlate(int inventoryPlate) {
        this.inventoryPlate = inventoryPlate;
    }

    public int getEquipmentNumber() {
        return equipmentNumber;
    }

    public void setEquipmentNumber(int equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAgentsNames() {
        return agentsNames;
    }

    public void setAgentsNames(String agentsNames) {
        this.agentsNames = agentsNames;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFeedbackSpecification() {
        return feedbackSpecification;
    }

    public void setFeedbackSpecification(String feedbackSpecification) {
        this.feedbackSpecification = feedbackSpecification;
    }

    public void setFeedbackRating(Integer feedbackRating) {
        this.feedbackRating = feedbackRating;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public Integer getFeedbackRating() {
        return feedbackRating;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(String feedbackDate) {
        this.feedbackDate = feedbackDate;
    }
}


