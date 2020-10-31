package com.helpdesk.HelpDesk.Models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Id_Gen")
    @GenericGenerator(
            name = "Id_Gen",
            strategy = "com.helpdesk.HelpDesk.Models.StringPrefixedSequenceIdGenerator",
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "FI"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d")
            })
    private String id;

    @Lob
    @NotNull
    private String specification;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar endingDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    private int inventoryPlate;

    @Max(10)
    @Min(0)
    private int equipmentNumber;

    @OneToOne
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "agent_request",
            joinColumns = {
                    @JoinColumn(name = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "username")
            })
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

    public String formatCreationDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return dateFormat.format(this.creationDate.getTime());
    }

    public String formatEndingDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return this.endingDate != null ? dateFormat.format(this.endingDate.getTime()) : null;
    }

    public enum Status{
        ACTIVO, CERRADO, CERRADO_SIN_CALIFICACION, CERRADO_POR_ESCALAMIENTO, NO_ASIGNADO
    }
}

