package com.helpdesk.HelpDesk;

import com.helpdesk.HelpDesk.DAO.CategoryDAO;
import com.helpdesk.HelpDesk.Models.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CRUDCategoryTest {

    public final boolean SUCCESFUL_CATEGORY_ACTION = true;
    public final boolean FAILED_CATEGORY_ACTION = false;

    @Autowired
    private CategoryDAO categoryDAO;

    @Test
    void DeactiveCategory() {
        Category cat = new Category("Baja de equipos");
        cat.setActive(false);
        System.out.println(cat.getName());
        System.out.println(categoryDAO.select(cat.getName()));
        assertEquals(SUCCESFUL_CATEGORY_ACTION, categoryDAO.update(categoryDAO.select(cat.getName()), cat));

        Category cat1 = new Category("Baja de equipos");
        cat1.setActive(false);
        assertEquals(FAILED_CATEGORY_ACTION, categoryDAO.update(categoryDAO.select(cat1.getName()), cat1));

        Category cat2 = new Category("Baja");
        cat2.setActive(false);
        assertEquals(FAILED_CATEGORY_ACTION, categoryDAO.update(categoryDAO.select(cat2.getName()), cat2));

    }

    @Test
    void AddCategory() {
        Category cat = new Category("Prueba");
        assertEquals(SUCCESFUL_CATEGORY_ACTION, categoryDAO.insert_update(cat));

        Category cat1 = new Category("Baja de equipos");
        assertEquals(SUCCESFUL_CATEGORY_ACTION, categoryDAO.update(categoryDAO.select(cat1.getName()), cat1));

        Category cat2 = new Category("Baja de equipos");
        assertEquals(FAILED_CATEGORY_ACTION, categoryDAO.update(categoryDAO.select(cat2.getName()), cat2));



    }
}
