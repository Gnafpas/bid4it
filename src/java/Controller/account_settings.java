/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Beans.Usersbean;
import DAOs.UsersDAO;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author adonis
 */

@ManagedBean (name = "account_settings")
@SessionScoped
public class account_settings implements Serializable{
  
    private String username;
    private boolean editmode;
    
    private Usersbean user = new Usersbean();

    public Usersbean getUser() {
        return user;
    }

    public void setUser(Usersbean user) {
        this.user = user;
    }
    
    public void setEditmode(boolean editmode) {
        this.editmode = editmode;
    }
        
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void edit() {
        editmode = true;
    }

    public void save(String field, String value) {
        
        UsersDAO bean=new UsersDAO();
        bean.changeInfo(user.getUsername(), field, value);
        editmode = false;
    }

    public boolean isEditmode() {
        return editmode;
    }
    
    
    public void init(String usrnm){
    
        UsersDAO bean=new UsersDAO();
        user = bean.getUser(usrnm); 
    
    }
    

}
