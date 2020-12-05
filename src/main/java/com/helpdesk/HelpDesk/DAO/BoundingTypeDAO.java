package com.helpdesk.HelpDesk.DAO;

import com.helpdesk.HelpDesk.Models.BoundingType;
import com.helpdesk.HelpDesk.Repository.BoundingTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class BoundingTypeDAO {

    private final BoundingTypeRepository boundingTypeRepository;

    public BoundingTypeDAO(BoundingTypeRepository boundingTypeRepository) {
        this.boundingTypeRepository = boundingTypeRepository;
    }

    public Iterable<BoundingType> select(){
        return boundingTypeRepository.findAll();
    }

    public BoundingType select(String name){
        return boundingTypeRepository.getName(name).iterator().next();
    }

    public boolean insert(BoundingType boundingType){
        try{
            this.boundingTypeRepository.save(boundingType);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean delete(BoundingType boundingType){
        Iterable<BoundingType> boundingTypes = this.boundingTypeRepository.findAll();
        for(BoundingType bt : boundingTypes){
            if(bt.getName().equals(boundingType.getName())){
                this.boundingTypeRepository.delete(bt);
                return true;
            }
        }
        return  false;
    }
}
