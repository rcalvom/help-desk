package com.helpdesk.HelpDesk.Models;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Request {

    @Id
    @NotBlank
    @Size(max = 16)
    private String id;

    @NotNull
    @Lob
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

    private enum Status{
        ACTIVO, CERRADO, CERRADO_SIN_CALIFICACION, CERRADO_POR_ESCALAMIENTO, NO_ASIGNADO
    }

}