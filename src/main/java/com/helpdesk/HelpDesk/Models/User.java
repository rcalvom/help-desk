package com.helpdesk.HelpDesk.Models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    private String username;

    private String name;
    private boolean isAgent;
    private boolean isAdministrator;

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public boolean isAgent() {
        return isAgent;
    }

    public boolean isAdministrator() {
        return isAdministrator;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAgent(boolean agent) {
        isAgent = agent;
    }

    public void setAdministrator(boolean administrator) {
        isAdministrator = administrator;
    }
}
