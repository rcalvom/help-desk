package com.helpdesk.HelpDesk.Models;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Rating {

    @Id
    @Size(max = 255)
    private String name;

    public Rating(){}

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
