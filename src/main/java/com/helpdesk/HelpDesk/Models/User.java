package com.helpdesk.HelpDesk.Models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

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

    @NotNull
    @Size(max = 255)
    private String location;

    private int phone;

    private int phoneExtension;

    @ManyToOne
    @NotNull
    private BoundingType boundingType;

    @ManyToOne
    @NotNull
    private Dependency dependency;

    @ManyToMany(mappedBy = "agents")
    private Set<Request> assignedRequests;

    @OneToMany(mappedBy = "user")
    private Set<Request> requests;

    public User() { }

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
        if(this.isAdministrator){
            roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        if(this.isAgent){
            roles.add(new SimpleGrantedAuthority("ROLE_AGENT"));
        }
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));

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

    public Set<Request> getAssignedRequests() {
        return assignedRequests;
    }

    public void setAssignedRequests(Set<Request> request) {
        this.assignedRequests = request;
    }

    public Set<Request> getRequests() {
        return requests;
    }

    public void setRequests(Set<Request> requests) {
        this.requests = requests;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getPhoneExtension() {
        return phoneExtension;
    }

    public void setPhoneExtension(int phoneExtension) {
        this.phoneExtension = phoneExtension;
    }
}
