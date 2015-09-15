/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.Serializable;

/**
 *
 * @author George
 */
public class Categoriesbean {
    
    private int categoryId;
    private String category;
    private String parent_category;
    
    public Categoriesbean (int categoryId,String category,String parent_category ) {
      
      this.categoryId=categoryId;
      this.category=category;
      this.parent_category=parent_category;
    }

    public Categoriesbean () {
      
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getParent_category() {
        return parent_category;
    }

    public void setParent_category(String parent_category) {
        this.parent_category = parent_category;
    }
    
    
    
}
