package com.helpdesk.HelpDesk.DAO;

import com.helpdesk.HelpDesk.Models.Feedback;
import com.helpdesk.HelpDesk.Repository.FeedbackRepository;
import org.springframework.stereotype.Service;

@Service
public class FeedbackDAO {

    private final FeedbackRepository feedbackRepository;

    public FeedbackDAO(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public Iterable<Feedback> select(){
        return feedbackRepository.findAll();
    }

    public boolean insert(Feedback feedback){
        try{
            feedbackRepository.save(feedback);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean update(Feedback oldFeedback, Feedback newFeedback){
        Iterable<Feedback> feedbacks = feedbackRepository.findAll();
        for(Feedback f : feedbacks){
            if(f.getId().equals(oldFeedback.getId())){
                f.setDate(newFeedback.getDate());
                f.setRating(newFeedback.getRating());
                f.setSpecification(newFeedback.getSpecification());
                f.setSuccessful(newFeedback.isSuccessful());
                feedbackRepository.save(f);
                return true;
            }
        }
        return false;
    }

    public boolean delete(Feedback feedback){
        Iterable<Feedback> feedbacks = feedbackRepository.findAll();
        for(Feedback f : feedbacks){
            if(f.getId().equals(feedback.getId())){
                feedbackRepository.delete(f);
                return true;
            }
        }
        return  false;
    }
}
