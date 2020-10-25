package com.helpdesk.HelpDesk.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

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
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar date;

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

    public Calendar getDate() {
        return this.date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    private enum Rating{
        EXCELENTE, BUENO, REGULAR, MALO, DEFICIENTE
    }

}
