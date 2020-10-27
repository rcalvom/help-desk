package com.helpdesk.HelpDesk.Models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Calendar;
import java.util.Set;

@Entity
public class Request {

    @Id
    @Size(max = 16)
    @NotBlank
    private String id;

    @Lob
    @NotNull
    private String specification;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar endingDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    private int inventoryPlate;

    @Max(10)
    private int equipmentNumber;

    @OneToOne
    private User user;

    @ManyToMany(mappedBy = "requests")
    private Set<User> agents;

    @ManyToOne
    private Category category;

    @OneToOne
    private Feedback feedback;


    public  Request(){}
    public Request(String specification, User user){
        this.specification = specification;
        this.creationDate = Calendar.getInstance();
        this.status = Status.NO_ASIGNADO;
        this.user = user;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public Category getCategory() {
        return category;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public void setEndingDate(Calendar endingDate) {
        this.endingDate = endingDate;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setInventoryPlate(int inventoryPlate) {
        this.inventoryPlate = inventoryPlate;
    }

    public void setEquipmentNumber(int equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAgents(Set<User> agents) {
        this.agents = agents;
    }

    public String getId() {
        return id;
    }

    public String getSpecification() {
        return specification;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public Calendar getEndingDate() {
        return endingDate;
    }

    public Status getStatus() {
        return status;
    }

    public int getInventoryPlate() {
        return inventoryPlate;
    }

    public int getEquipmentNumber() {
        return equipmentNumber;
    }

    public User getUser() {
        return user;
    }

    public Set<User> getAgents() {
        return agents;
    }

    public enum Status{
        ACTIVO, CERRADO, CERRADO_SIN_CALIFICACION, CERRADO_POR_ESCALAMIENTO, NO_ASIGNADO
    }
}

