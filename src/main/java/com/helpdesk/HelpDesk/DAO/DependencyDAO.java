package com.helpdesk.HelpDesk.DAO;

import com.helpdesk.HelpDesk.Models.Dependency;
import com.helpdesk.HelpDesk.Repository.DependencyRepository;
import org.springframework.stereotype.Service;

@Service
public class DependencyDAO {

    private final DependencyRepository dependencyRepository;

    public DependencyDAO(DependencyRepository dependencyRepository) {
        this.dependencyRepository = dependencyRepository;
    }

    public Iterable<Dependency> select(){
        return dependencyRepository.findAll();
    }

    public Dependency select(String name){
        return dependencyRepository.getName(name).iterator().next();
    }

    public boolean insert(Dependency dependency){
        try{
            this.dependencyRepository.save(dependency);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean delete(Dependency dependency){
        Iterable<Dependency> dependencies = this.dependencyRepository.findAll();
        for(Dependency d : dependencies){
            if(d.getName().equals(dependency.getName())){
                this.dependencyRepository.delete(d);
                return true;
            }
        }
        return  false;
    }

}
