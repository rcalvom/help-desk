package com.helpdesk.HelpDesk.Forms;

import com.helpdesk.HelpDesk.Models.BoundingType;
import com.helpdesk.HelpDesk.Models.Request;
import com.helpdesk.HelpDesk.Models.User;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import java.util.ArrayList;
import java.util.List;

public class BoundingTypeReportForm {

    @CsvBindByName(column = "Tipo de vinculación")
    @CsvBindByPosition(position = 0)
    private String boundingType;

    @CsvBindByName(column = "Numero de equipos")
    @CsvBindByPosition(position = 1)
    private Integer pos0;

    @CsvBindByName(column = "Numero de solicitudes")
    @CsvBindByPosition(position = 2)
    private Integer pos1;

    @CsvBindByName(column = "Promedio de calificación")
    @CsvBindByPosition(position = 3)
    private Integer pos2;

    @CsvBindByName(column = "Eficacia")
    @CsvBindByPosition(position = 4)
    private Integer pos3;

    public BoundingTypeReportForm(BoundingType boundingType, boolean toShow[]) {

        this.pos0 = null;
        this.pos1 =null;
        this.pos2 = null;
        this.pos3 = null;
        this.boundingType = boundingType.getName();

        Integer numbers[] = new Integer[toShow.length];
        int numberClosedRequests = 0;
        for(int i = 0; i < toShow.length; ++i){
            if(toShow[i]){
                numbers[i] = 0;
            }
        }
        for(User user : boundingType.getUsers()){
            for(Request request : user.getRequests()){
                numbers[0] += request.getEquipmentNumber();
                numbers[1]++;
                if(request.getStatus() == Request.Status.CERRADO){
                    numberClosedRequests++;
//                if(toShow[2]) numbers[2] += request.getFeedback().getRating();
                    //CUANDO ESTÉ DISPONIBLE
//                if(request.getFeedback().getSuccessful()){
//                    numbers[3]++;
//                }
                }
            }
        }

        numbers[2] /= numberClosedRequests;
        numbers[3] /= numberClosedRequests;

        for(int i = 0; i < toShow.length; ++i){
            if(toShow[i]){
                if(pos0 == null){
                    pos0 = numbers[i];
                }else if(pos1 == null){
                    pos1 = numbers[i];
                }else if(pos2 == null){
                    pos2 = numbers[i];
                }else if(pos3 == null){
                    pos3 = numbers[i];
                }
            }
        }
    }

    public BoundingTypeReportForm(){ }

    public String getBoundingType() {
        return boundingType;
    }

    public void setBoundingType(String boundingType) {
        this.boundingType = boundingType;
    }

    public void setPos0(Integer pos0) {
        this.pos0 = pos0;
    }

    public void setPos1(Integer pos1) {
        this.pos1 = pos1;
    }

    public void setPos2(Integer pos2) {
        this.pos2 = pos2;
    }

    public void setPos3(Integer pos3) {
        this.pos3 = pos3;
    }

    public Integer getPos0() {
        return pos0;
    }

    public Integer getPos1() {
        return pos1;
    }

    public Integer getPos2() {
        return pos2;
    }

    public Integer getPos3() {
        return pos3;
    }

}
