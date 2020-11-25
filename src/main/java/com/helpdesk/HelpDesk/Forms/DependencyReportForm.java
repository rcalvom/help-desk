package com.helpdesk.HelpDesk.Forms;

import com.helpdesk.HelpDesk.Models.Dependency;
import com.helpdesk.HelpDesk.Models.Request;
import com.helpdesk.HelpDesk.Models.User;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import java.util.ArrayList;
import java.util.List;

public class DependencyReportForm {

    public String[]  columnas = {"Dependencia", "Numero de equipos","Numero de solicitudes",
            "Numero de solicitudes excelentes",
            "Numero de solicitudes buenas",
            "Numero de solicitudes regulares",
            "Numero de solicitudes malas",
            "Numero de solicitudes deficientes"};
    @CsvBindByName(column = "Dependencia")
    @CsvBindByPosition(position = 0)
    private String dependency;

    @CsvBindByName(column = "Numero de equipos")
    @CsvBindByPosition(position = 1)
    private Integer pos0;

    @CsvBindByName(column = "Numero de solicitudes")
    @CsvBindByPosition(position = 2)
    private Integer pos1;

    @CsvBindByName(column = "Numero de solicitudes excelentes")
    @CsvBindByPosition(position = 3)
    private Integer pos2;

    @CsvBindByName(column = "Numero de solicitudes buenas")
    @CsvBindByPosition(position = 4)
    private Integer pos3;

    @CsvBindByName(column = "Numero de solicitudes regulares")
    @CsvBindByPosition(position = 5)
    private Integer pos4;

    @CsvBindByName(column = "Numero de solicitudes malas")
    @CsvBindByPosition(position = 6)
    private Integer pos5;

    @CsvBindByName(column = "Numero de solicitudes deficientes")
    @CsvBindByPosition(position = 7)
    private Integer pos6;

    public DependencyReportForm(Dependency dependency, boolean[] toShow) {

        this.pos0 = null;
        this.pos1 =null;
        this.pos2 = null;
        this.pos3 = null;
        this.pos4 = null;
        this.pos5 = null;
        this.pos6 = null;
        this.dependency = dependency.getName();


        Integer numeros[] = new Integer[toShow.length];
        for(int i = 0; i < toShow.length; ++i){
            if(toShow[i]){
                numeros[i] = 0;
            }
        }
//        Integer equipmentNumber = (toShow[0] ? 0 : null);
//        Integer requestNumber = (toShow[1] ? 0 : null);
//        Integer goodNumber = (toShow[2] ? 0 : null);
//        Integer excelentNumber = (toShow[3] ? 0 : null);
//        Integer regularNumber = (toShow[4] ? 0 : null);
//        Integer badNumber = (toShow[5] ? 0 : null);
//        Integer deficientNumber = (toShow[6] ? 0 : null);

        for(User user : dependency.getUsers()){
            for(Request request : user.getRequests()){
                if(toShow[0]) numeros[0] += request.getEquipmentNumber();
                if(toShow[1]) numeros[1]++;
                if(request.getStatus() == Request.Status.CERRADO){
                    switch (request.getFeedback().getRating().getName()){
                        case "Excelente":
                            if(toShow[2]) numeros[2]++;
                            break;
                        case "Bueno":
                            if(toShow[3]) numeros[3]++;
                            break;
                        case "Regular":
                            if(toShow[4]) numeros[4]++;
                            break;
                        case "Malo":
                            if(toShow[5]) numeros[5]++;
                            break;
                        case "Deficiente":
                            if(toShow[6]) numeros[6]++;
                            break;
                    }
                }
            }
        }

        for(int i = 0; i < toShow.length; ++i){
            if(toShow[i]){
                if(pos0 == null){
                    pos0 = numeros[i];
                }else if(pos1 == null){
                    pos1 = numeros[i];
                }else if(pos2 == null){
                    pos2 = numeros[i];
                }else if(pos3 == null){
                    pos3 = numeros[i];
                }else if(pos4 == null){
                    pos4 = numeros[i];
                }else if(pos5 == null){
                    pos5 = numeros[i];
                }else if(pos6 == null){
                    pos6 = numeros[i];
                }
            }
        }

    }

    public DependencyReportForm(){ }

    public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }

    public Integer getpos0() {
        return pos0;
    }

    public void setpos0(int pos0) {
        this.pos0 = pos0;
    }

    public Integer getpos1() {
        return pos1;
    }

    public void setpos1(int pos1) {
        this.pos1 = pos1;
    }

    public Integer getpos2() {
        return pos2;
    }

    public void setpos2(int pos2) {
        this.pos2 = pos2;
    }

    public Integer getpos3() {
        return pos3;
    }

    public void setpos3(int pos3) {
        this.pos3 = pos3;
    }

    public Integer getpos4() {
        return pos4;
    }

    public void setpos4(int pos4) {
        this.pos4 = pos4;
    }

    public Integer getpos5() {
        return pos5;
    }

    public void setpos5(int pos5) {
        this.pos5 = pos5;
    }

    public Integer getpos6() {
        return pos6;
    }

    public void setpos6(int pos6) {
        this.pos6 = pos6;
    }
}
