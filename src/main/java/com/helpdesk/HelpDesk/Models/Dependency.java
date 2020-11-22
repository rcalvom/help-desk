package com.helpdesk.HelpDesk.Models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.security.PublicKey;
import java.util.Set;

@Entity
public class Dependency {

    @Id
    @Size(max = 255)
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "dependency")
    private Set<User> users;

    public Dependency() {}

    public Dependency(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
