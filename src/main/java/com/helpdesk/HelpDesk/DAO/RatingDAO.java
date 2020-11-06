package com.helpdesk.HelpDesk.DAO;

import com.helpdesk.HelpDesk.Models.Rating;
import com.helpdesk.HelpDesk.Repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RatingDAO {

    @Autowired
    private RatingRepository ratingRepository;

    public Iterable<Rating> select(){
        return ratingRepository.findAll();
    }

}
