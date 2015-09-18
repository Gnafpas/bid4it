/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import Beans.Itemsbean;
import java.io.Serializable;

/**
 *
 * @author George
 */
public class Seller_items implements Serializable{
    
    private Itemsbean item;
    private String status;
    private boolean  has_image;

    public Itemsbean getItem() {
        return item;
    }

    public void setItem(Itemsbean item) {
        this.item = item;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isHas_image() {
        return has_image;
    }

    public void setHas_image(boolean has_image) {
        this.has_image = has_image;
    }
   
}
