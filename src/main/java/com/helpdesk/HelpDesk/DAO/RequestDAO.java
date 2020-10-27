package com.helpdesk.HelpDesk.DAO;

import com.helpdesk.HelpDesk.Models.Request;
import com.helpdesk.HelpDesk.Models.User;
import com.helpdesk.HelpDesk.Repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RequestDAO {

    private final RequestRepository requestRepository;

    public RequestDAO(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public Iterable<Request> select(){
        return requestRepository.findAll();
    }

    public boolean insert(Request request){
        try{
            requestRepository.save(request);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean update(Request oldRequest, Request newRequest){
        Iterable<Request> requests = requestRepository.findAll();
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
                return true;
            }
        }
        return false;
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

    public Iterable<Request> selectByStatus(String status){
        return requestRepository.getRequestByState(status);
    }

}
