package com.helpdesk.HelpDesk.DAO;

import com.helpdesk.HelpDesk.Models.Category;
import com.helpdesk.HelpDesk.Repository.CategoryRepository;

public class CategoryDAO {
    private CategoryRepository categoryRepository;


    public Iterable<Category> select(){
        return categoryRepository.findAll();
    }

    public boolean insert(Category category){
        try{
            categoryRepository.save(category);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean update(Category oldCategory, Category newCategory){
        Iterable<Category> categorys = categoryRepository.findAll();
        for(Category c : categorys){
            if(c.getId().equals(oldCategory.getId())){
                c.setName(newCategory.getName());
                return true;
            }
        }
        return false;
    }

    public boolean delete(Category category){
        Iterable<Category> categorys = categoryRepository.findAll();
        for(Category c : categorys){
            if(c.getId().equals(category.getId())){
                categoryRepository.delete(c);
                return true;
            }
        }
        return  false;
    }
}