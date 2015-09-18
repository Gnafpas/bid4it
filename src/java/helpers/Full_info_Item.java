/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import Beans.Bidsbean;
import Beans.Categoriesbean;
import Beans.Itemsbean;
import java.util.List;

/**
 *
 * @author George
 */
public class Full_info_Item {
    
    private Itemsbean item;
    private List <Categoriesbean> Category;
    private List <Bidsbean> Bid;

    public Itemsbean getItem() {
        return item;
    }

    public void setItem(Itemsbean item) {
        this.item = item;
    }

    public List<Categoriesbean> getCategory() {
        return Category;
    }

    public void setCategory(List<Categoriesbean> Category) {
        this.Category = Category;
    }

    public List<Bidsbean> getBid() {
        return Bid;
    }

    public void setBid(List<Bidsbean> Bid) {
        this.Bid = Bid;
    }
    
    
    
}
