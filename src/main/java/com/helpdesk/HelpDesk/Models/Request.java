package com.helpdesk.HelpDesk.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;
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
    private Date creationDate;

    private Date endingDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Size(max = 10)
    private int inventoryPlate;

    @Positive
    @Size(max = 10)
    private int equipmentNumber;

    @OneToOne
    private User user;

    @ManyToMany(mappedBy = "requests")
    @NotNull
    private Set<User> agents;

    @ManyToOne
    private Category category;

    @OneToOne
    private Feedback feedback;


    public  Request(){}
    public Request(String specification, User user){
        this.specification = specification;
        this.creationDate = new Date();
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

    private enum Status{
        ACTIVO, CERRADO, CERRADO_SIN_CALIFICACION, CERRADO_POR_ESCALAMIENTO, NO_ASIGNADO
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setEndingDate(Date endingDate) {
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

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getEndingDate() {
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
}