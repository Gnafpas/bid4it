/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author adonis
 */

@ManagedBean (name = "searchParameters")
@RequestScoped
public class searchParameters implements Serializable{

   // @ManagedProperty(value = "#{param.searchSTR}")
    String searchSTR;

    public void setSearchSTR(String searchSTR) {
        this.searchSTR = searchSTR;
    }

    // Getters and Setters

    public String getSearchSTR() {
        return searchSTR;
    }
}