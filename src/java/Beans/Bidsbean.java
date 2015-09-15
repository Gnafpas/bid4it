/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author George
 */
public class Bidsbean implements Serializable {
    
    private int bid_itemid;
    private String bidder;
    private Date time;
    private int amount;

    public int getBid_itemid() {
        return bid_itemid;
    }

    public void setBid_itemid(int bid_itemid) {
        this.bid_itemid = bid_itemid;
    }

    public String getBidder() {
        return bidder;
    }

    public void setBidder(String bidder) {
        this.bidder = bidder;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    
    
}
