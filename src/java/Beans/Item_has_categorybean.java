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
public class Item_has_categorybean implements Serializable {
    
    private int itemId;
    private int categoryId;
    
    
    public Item_has_categorybean (int itemId,int categoryId ) {
      
      this.itemId=itemId;
      this.categoryId=categoryId;
    }

    public Item_has_categorybean () {
      
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    
    
}
