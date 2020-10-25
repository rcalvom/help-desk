package com.helpdesk.HelpDesk.DAO;

import com.helpdesk.HelpDesk.Models.Dependency;
import com.helpdesk.HelpDesk.Repository.DependencyRepository;

public class DependencyDAO {

    private DependencyRepository dependencyRepository;


    public Iterable<Dependency> select(){
        return dependencyRepository.findAll();
    }

    public boolean insert(Dependency dependency){
        try{
            dependencyRepository.save(dependency);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean update(Dependency oldDependency, Dependency newDependency){
        Iterable<Dependency> dependencys = dependencyRepository.findAll();
        /*for(Dependency d : dependencys){
            if(d.getName().equals(oldDependency.getName())){
                return true;
            }
        }*/
        return false;
    }

    public boolean delete(Dependency dependency){
        Iterable<Dependency> dependencys = dependencyRepository.findAll();
        for(Dependency d : dependencys){
            if(d.getName().equals(dependency.getName())){
                dependencyRepository.delete(d);
                return true;
            }
        }
        return  false;
    }

}
