package com.helpdesk.HelpDesk.DAO;

import com.helpdesk.HelpDesk.Models.User;
import com.helpdesk.HelpDesk.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDAO implements UserDetailsService{

    private final UserRepository userRepository;

    public UserDAO(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> select(){
        return userRepository.findAll();
    }
    public boolean insert(User user){
        try{
            this.userRepository.save(user);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean update(User oldUser, User newUser){
        if(oldUser.isAgent() != newUser.isAgent()){
            Iterable<User> users = this.userRepository.findAll();
            for(User u : users){
                if(u.getUsername().equals(oldUser.getUsername())){
                    u.setAgent(newUser.isAgent());
                    u.setName(newUser.getName());
                    u.setAdministrator(newUser.isAdministrator());
                    u.setBoundingType(newUser.getBoundingType());
                    u.setDependency(newUser.getDependency());
                    u.setLocation(newUser.getLocation());
                    u.setPhone(newUser.getPhone());
                    u.setPhoneExtension(newUser.getPhoneExtension());
                    this.userRepository.save(u);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean delete(User user){
        Iterable<User> users = this.userRepository.findAll();
        for(User u : users){
            if(u.getUsername().equals(user.getUsername())){
                this.userRepository.delete(u);
                return true;
            }
        }
        return  false;
    }

    public Iterable<User> selectAgent(){
        return this.userRepository.getUserAgent();
    }

    public Iterable<User> selectUser(){
        return this.userRepository.getUserUser();
    }

    public User selectAgent(String username){
        try{
            return this.userRepository.getAgentByUsername(username).iterator().next();
        }catch (Exception e){
            return null;
        }
    }

    public User selectUser(String username){
        try {
            return this.userRepository.getUserByUsername(username).iterator().next();
        }catch (Exception e){
            return null;
        }
    }

    public User selectAdmin(){
        try {
            return this.userRepository.getAdministrator().iterator().next();
        }catch (Exception e){
            return null;
        }
    }

    public User selectPerson(String username){
        try {
            return this.userRepository.getPerson(username).iterator().next();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.selectPerson(username);
    }

}
