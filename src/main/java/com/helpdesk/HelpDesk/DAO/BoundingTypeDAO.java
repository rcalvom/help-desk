package com.helpdesk.HelpDesk.DAO;

import com.helpdesk.HelpDesk.Models.BoundingType;
import com.helpdesk.HelpDesk.Repository.BoundingTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BoundingTypeDAO {

    @Autowired
    private BoundingTypeRepository boundingTypeRepository;

    public Iterable<BoundingType> select(){
        return boundingTypeRepository.findAll();
    }

    public boolean insert(BoundingType boundingType){
        try{
            boundingTypeRepository.save(boundingType);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean update(BoundingType oldBoundingType, BoundingType newBoundingType){
        Iterable<BoundingType> boundingTypes = boundingTypeRepository.findAll();
        /*for(BoundingType bt : boundingTypes){
            if(bt.getName().equals(oldBoundingType.getName())){
                return true;
            }
        }*/
        return false;
    }

    public boolean delete(BoundingType boundingType){
        Iterable<BoundingType> boundingTypes = boundingTypeRepository.findAll();
        for(BoundingType bt : boundingTypes){
            if(bt.getName().equals(boundingType.getName())){
                boundingTypeRepository.delete(bt);
                return true;
            }
        }
        return  false;
    }
}
