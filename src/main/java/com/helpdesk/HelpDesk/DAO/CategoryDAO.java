package com.helpdesk.HelpDesk.DAO;

import com.helpdesk.HelpDesk.Models.Category;
import com.helpdesk.HelpDesk.Repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryDAO {

    private final CategoryRepository categoryRepository;

    public CategoryDAO(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Iterable<Category> select(){
        return categoryRepository.findAll();
    }

    public boolean insert_update(Category category){
        try{
            this.categoryRepository.save(category);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean update(Category oldCategory, Category newCategory){
        if(oldCategory != null && newCategory != null && oldCategory.isActive() != newCategory.isActive()){
            Iterable<Category> categories = this.categoryRepository.findAll();
            for(Category c : categories){
                if(c.getName().equals(oldCategory.getName())){
                    c.setName(newCategory.getName());
                    c.setActive(newCategory.isActive());
                    this.categoryRepository.save(c);
                    return true;
                }
            }
        }
        return false;

    }

    public boolean delete(Category category){
        Iterable<Category> categories = this.categoryRepository.findAll();
        for(Category c : categories){
            if(c.getName().equals(category.getName())){
                this.categoryRepository.delete(c);
                return true;
            }
        }
        return  false;
    }

    public Category select(String name){
        try {
            return this.categoryRepository.getCategoryByName(name).iterator().next();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Iterable<Category> selectActiveCategories(){
        return categoryRepository.getActiveCategories();
    }

}