package com.helpdesk.HelpDesk.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String specification;
    private enum rating{
        EXCELENTE("Excelente"),
        BUENO("Bueno"),
        REGURLAR("Regular"),
        MALO("Malo"),
        DEFICIENTE("Deficiente");

        private final String label;
        private rating(String label){
            this.label = label;
        }
    }
    private Date date;

}
