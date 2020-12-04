package com.helpdesk.HelpDesk.DAO;

import com.helpdesk.HelpDesk.Models.Request;
import com.helpdesk.HelpDesk.Models.User;
import com.helpdesk.HelpDesk.Repository.RequestRepository;
import com.nimbusds.jose.util.JSONObjectUtils;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.stereotype.Service;

@Service
public class RequestDAO {

    private final RequestRepository requestRepository;

    public RequestDAO(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public Iterable<Request> select(){
        return requestRepository.findAll();
    }


    public boolean isModifiable(Request request1, Request request2){

        if((request2.getStatus() == Request.Status.CERRADO_SIN_CALIFICACION || request2.getStatus() == Request.Status.CERRADO) != (request2.getEndingDate() != null && request2.getAgents() != null)){
            return false;
        }
        if((request1.getAgents() != request2.getAgents()) || (request1.getEquipmentNumber() != request2.getEquipmentNumber()) || (request1.getInventoryPlate() != request2.getInventoryPlate()) || (request1.getStatus() != request2.getStatus()) || (request1.getCategory() != request2.getCategory())){

            if(request1.getCreationDate() != null && (request1.getCreationDate() != request2.getCreationDate())){
                return false;
            }

            if(request1.getEndingDate() != null && (request1.getEndingDate() != request2.getEndingDate())){
                return false;
            }

            if(request1.getSpecification() != null && (request1.getSpecification() != request2.getSpecification())){
                return false;
            }

            if(request1.getUser() != null && (request1.getUser() != request2.getUser())){
                return false;
            }

            if(request1.getFeedback() != null && (request1.getFeedback() != request2.getFeedback())){
                return false;
            }

            return true;
        }
        else{
            if((request1.getCreationDate() == null && request2.getCreationDate() != null) || (request1.getEndingDate() == null && request2.getEndingDate() != null) || (request1.getSpecification() == null && request2.getSpecification() != null) || (request1.getUser() == null && request2.getUser() != null) || (request1.getFeedback() == null && request2.getFeedback() != null)){
                System.out.println(request1.getFeedback().getRating() + " " + request2.getFeedback().getRating());
                return true;
            }
            else{
                return false;
            }
        }
    }

    public boolean validateInsertRequest(Request request){
        if(request == null){
            return false;
        }
        if((request.getAgents() == null) == (request.getStatus() == Request.Status.ACTIVO)){
            return false;
        }
        return (request.getAgents() == null) == (request.getCategory() == null);
    }

    public boolean insert(Request request){
        if(validateInsertRequest(request)){
            try{
                requestRepository.save(request);
            }catch (Exception e){
                System.out.println(e.getMessage());
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean update(Request oldRequest, Request newRequest){
        Iterable<Request> requests = requestRepository.findAll();
        if(isModifiable(oldRequest, newRequest)){
            for(Request r : requests){
                if(r.getId().equals(oldRequest.getId())){
                    r.setAgents(newRequest.getAgents());
                    r.setCreationDate(newRequest.getCreationDate());
                    r.setEndingDate(newRequest.getEndingDate());
                    r.setEquipmentNumber(newRequest.getEquipmentNumber());
                    r.setInventoryPlate(newRequest.getInventoryPlate());
                    r.setSpecification(newRequest.getSpecification());
                    r.setStatus(newRequest.getStatus());
                    r.setUser(newRequest.getUser());
                    r.setFeedback(newRequest.getFeedback());
                    r.setCategory(newRequest.getCategory());
                    try{
                        requestRepository.save(r);
                    }catch (Exception e){
                        return false;
                    }
                }
            }
            return true;
        }
        else{
            return false;
        }

    }

    public boolean delete(Request request){
        Iterable<Request> requests = requestRepository.findAll();
        for(Request r : requests){
            if(r.getId().equals(request.getId())){
                requestRepository.delete(r);
                return true;
            }
        }
        return  false;
    }

    public Iterable<Request> selectByUser(User user){
        return requestRepository.getRequestByUser(user.getUsername());
    }

    public Iterable<Request> selectByAgent(User user){
        return requestRepository.getRequestByAgent(user.getUsername());
    }


    public Iterable<Request> selectByStatus(Request.Status status){
        return requestRepository.getRequestByState(status.name());
    }

    public Request selectById(String id){
        try{
            return requestRepository.getRequestById(id).iterator().next();
        }catch (Exception e){
            return null;
        }
    }

}
