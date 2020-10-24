package com.helpdesk.HelpDesk.Models;

import com.sun.istack.NotNull;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.Date;

@Entity
public class Request {
    @Id
    //@Size(255)
    private String id;

    @NotNull
    @Lob
    private String specification;
    private Date creationDate;
    private Date endingDate;
    private String status;
    public enum MyStatus{
        AC("Activo"),
        CL("Cerrado"),
        CLNR("Cerrado sin calificacion"),
        CLE("Cerrado por escalamiento"),
        NA("Sin asignar");
        public final String label;
        private MyStatus(String label){
            this.label = label;
        }
    };

    private int inventoryPlate;
    private int equipmentNumber;

    public Request(){}
    public Request(String id, String specification, int equipmentNumber ){
        this.id = id;
        this.specification = specification;
        this.creationDate = new Date();
        this.status = MyStatus.AC.label;
        this.equipmentNumber = equipmentNumber;

        //FALTAN FEEDBACK, USERNAME, CATEGORYNAME

    }

    public String getId() {
        return id;
    }

    public String getSpecification() {
        return specification;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    public int getInventoryPlate() {
        return inventoryPlate;
    }

    public int getEquipmentNumber() {
        return equipmentNumber;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public void setInventoryPlate(int inventoryPlate) {
        this.inventoryPlate = inventoryPlate;
    }

    public void setEquipmentNumber(int equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }
}
