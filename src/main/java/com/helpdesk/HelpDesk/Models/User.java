package com.helpdesk.HelpDesk.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class User {

    @Id
    @Size(max = 16)
    @NotBlank
    private String username;

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotNull
    private boolean isAgent;

    @NotNull
    private boolean isAdministrator;

    @ManyToOne
    @NotNull
    private BoundingType boundingType;

    @ManyToOne
    @NotNull
    private Dependency dependency;

    @ManyToMany(mappedBy = "agents")
    private Set<Request> requests;

    public User(){}
    public User(String username, String name, BoundingType boundingType, Dependency dependency){
        this.name = name;
        this.isAgent = false;
        this.isAdministrator = false;
        this.boundingType = boundingType;
        this.dependency = dependency;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAgent() {
        return this.isAgent;
    }

    public void setAgent(boolean agent) {
        this.isAgent = agent;
    }

    public boolean isAdministrator() {
        return this.isAdministrator;
    }

    public void setAdministrator(boolean administrator) {
        this.isAdministrator = administrator;
    }

    public BoundingType getBoundingType() {
        return this.boundingType;
    }

    public void setBoundingType(BoundingType boundingType) {
        this.boundingType = boundingType;
    }

    public Dependency getDependency() {
        return this.dependency;
    }

    public void setDependency(Dependency dependency) {
        this.dependency = dependency;
    }

    public Set<Request> getRequest() {
        return requests;
    }

    public void setRequest(Set<Request> request) {
        this.requests = request;
    }
}
