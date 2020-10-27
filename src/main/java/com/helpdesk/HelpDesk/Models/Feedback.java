package com.helpdesk.HelpDesk.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Lob
    private String specification;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Rating rating;

    @NotNull
    private Date date;

    public Feedback(){}
    public Feedback(String specification, Rating rating){
        this.specification = specification;
        this.rating = rating;
        this.date = new Date();
    }


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpecification() {
        return this.specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Rating getRating() {
        return this.rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private enum Rating{
        EXCELENTE, BUENO, REGULAR, MALO, DEFICIENTE
    }

}
