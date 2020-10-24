package com.helpdesk.HelpDesk.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
public class Request {

    @Id
    @Size(max = 16)
    @NotBlank
    private String id;

    @Lob
    @NotNull
    private String specification;

    @NotNull
    private Date creationDate;

    private Date endingDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Size(max = 10)
    private int inventoryPlate;

    @Positive
    @Size(max = 10)
    private int equipmentNumber;

    @OneToOne
    private User user;

    @ManyToMany(mappedBy = "requests")
    @NotNull
    private Set<User> agents;

    private enum Status{
        ACTIVO, CERRADO, CERRADO_SIN_CALIFICACION, CERRADO_POR_ESCALAMIENTO, NO_ASIGNADO
    }

}