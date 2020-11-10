package com.helpdesk.HelpDesk.Models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
public class User implements UserDetails {

    @Id
    @Size(max = 32)
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

    public User() {}

    public User(String username, String name, BoundingType boundingType, Dependency dependency){
        this.username = username;
        this.name = name;
        this.isAgent = false;
        this.isAdministrator = false;
        this.boundingType = boundingType;
        this.dependency = dependency;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        /*if(this.isAdministrator){
            roles.add(new SimpleGrantedAuthority("admin"));
        }
        if(this.isAgent){
            roles.add(new SimpleGrantedAuthority("agent"));
        }
        roles.add(new SimpleGrantedAuthority("user"));*/

        if(this.isAdministrator){
            roles.add(new SimpleGrantedAuthority("admin"));
        }else if(this.isAgent){
            roles.add(new SimpleGrantedAuthority("agent"));
        }else {
            roles.add(new SimpleGrantedAuthority("user"));
        }
        return roles;
    }

    @Override
    public String getPassword() {
        return null;
    }

    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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
