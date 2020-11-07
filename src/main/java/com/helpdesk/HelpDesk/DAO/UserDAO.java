package com.helpdesk.HelpDesk.DAO;

import com.helpdesk.HelpDesk.Models.User;
import com.helpdesk.HelpDesk.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDAO {

    @Autowired
    private UserRepository userRepository;

    public Iterable<User> select(){
        return userRepository.findAll();
    }
    public boolean insert(User user){
        try{
            userRepository.save(user);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean update(User oldUser, User newUser){
        Iterable<User> users = userRepository.findAll();
        for(User u : users){
            if(u.getUsername().equals(oldUser.getUsername())){
                u.setAgent(newUser.isAgent());
                u.setName(newUser.getName());
                u.setAdministrator(newUser.isAdministrator());
                u.setBoundingType(newUser.getBoundingType());
                u.setDependency(newUser.getDependency());
                userRepository.save(u);
                return true;
            }
        }
        return false;
    }

    public boolean delete(User user){
        Iterable<User> users = userRepository.findAll();
        for(User u : users){
            if(u.getUsername().equals(user.getUsername())){
                userRepository.delete(u);
                return true;
            }
        }
        return  false;
    }

    public Iterable<User> selectAgent(){
        return userRepository.getUserAgent();
    }

    public Iterable<User> selectUser(){
        return userRepository.getUserUser();
    }

    public User selectAgent(String username){
        try{
            return userRepository.getAgentByUsername(username).iterator().next();
        }catch (Exception e){
            return null;
        }
    }

    public User selectUser(String username){
        try {
            return  userRepository.getUserByUsername(username).iterator().next();
        }catch (Exception e){
            return null;
        }
    }

    public User selectAdmin(){
        try {
            return userRepository.getAdminsitrator().iterator().next();
        }catch (Exception e){
            return null;
        }
    }

    public User selectPerson(String username){
        try {
            return userRepository.getPerson(username).iterator().next();
        }catch (Exception e){
            return null;
        }
    }

}
