package com.helpdesk.HelpDesk.Forms;

public class CreateRequestForm {

    private String description;
    private Integer inventoryPlate;
    private int equipmentNumber;

    public CreateRequestForm() {}

    public void setInventoryPlate(Integer inventoryPlate) {
        this.inventoryPlate = inventoryPlate;
    }

    public void setEquipmentNumber(int equipmentNumber) {
        this.equipmentNumber = equipmentNumber;
    }

    public Integer getInventoryPlate() {
        return inventoryPlate;
    }

    public int getEquipmentNumber() {
        return equipmentNumber;
    }

    public CreateRequestForm(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
