package com.helpdesk.HelpDesk.DAO;

import com.helpdesk.HelpDesk.Models.Category;
import com.helpdesk.HelpDesk.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryDAO {

    @Autowired
    private CategoryRepository categoryRepository;

    public Iterable<Category> select(){
        return categoryRepository.findAll();
    }

    public  CategoryDAO(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
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
        Iterable<Category> categories = categoryRepository.findAll();
        for(Category c : categories){
            if(c.getName().equals(oldCategory.getName())){
                c.setName(newCategory.getName());
                c.setActive(newCategory.isActive());
                categoryRepository.save(c);
                return true;
            }
        }
        return false;
    }

    public boolean delete(Category category){
        Iterable<Category> categories = categoryRepository.findAll();
        for(Category c : categories){
            if(c.getName().equals(category.getName())){
                categoryRepository.delete(c);
                return true;
            }
        }
        return  false;
    }
    public Category select(String name){
        try {
            return categoryRepository.getCategoryByName(name).iterator().next();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Iterable<Category> selectActiveCategories(){
        return categoryRepository.getActiveCategories();
    }

}