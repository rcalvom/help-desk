package com.helpdesk.HelpDesk.Models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;


@Entity
public class Category {

    @Id
    @Size(max = 255)
    private String name;

    @NotNull
    private boolean isActive;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
    private Set<Request> requests;

    public Category() {}

    public Category(String name){
        this.name = name;
        this.isActive = true;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Set<Request> getRequests() {
        return requests;
    }

    public void setRequests(Set<Request> requests) {
        this.requests = requests;
    }
}
