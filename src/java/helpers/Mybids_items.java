/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import Beans.Bidsbean;
import Beans.Itemsbean;

/**
 *
 * @author George
 */
public class Mybids_items {
    
    private Itemsbean item;
    private Bidsbean bids;
    private boolean  has_image;
    private boolean completed=false;
    private boolean Iwin=false;

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isIwin() {
        return Iwin;
    }

    public void setIwin(boolean Iwin) {
        this.Iwin = Iwin;
    }
    
    public Itemsbean getItem() {
        return item;
    }

    public void setItem(Itemsbean item) {
        this.item = item;
    }

    public boolean isHas_image() {
        return has_image;
    }

    public void setHas_image(boolean has_image) {
        this.has_image = has_image;
    }

    public Bidsbean getBids() {
        return bids;
    }

    public void setBids(Bidsbean bids) {
        this.bids = bids;
    }
    
    
    
}
