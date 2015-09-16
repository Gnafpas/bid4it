/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;
import DAOs.UsersDAO;
import Beans.Usersbean;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.context.FacesContext;

/**
 *
 * @author George
 */
@ManagedBean (name = "headerbean")
@SessionScoped

public class header implements Serializable{
    
    Usersbean  user=null;
    String searchSTR;
    String searchCAT;
   
    public void setSearchSTR(String searchSTR) {
        this.searchSTR = searchSTR;
    }

    public String getSearchSTR() {
        return searchSTR;
    }
    
    public void setSearchCAT(String searchCAT) {
        this.searchCAT = searchCAT;
    }

    public String getSearchCAT() {
        return searchCAT;
    }
    
    public Usersbean getUser() {
        return user;
    }

    public boolean loggedin_user(){
        
        String usrname=SessionBean.getUserName();
        if(usrname==null)
          return false;
        else{
            UsersDAO bean=new UsersDAO();
            user= bean.getUser(usrname);
            return  true;
       }
    }
    
    public String logout(){
        HttpSession session = SessionBean.getSession();
        try{
            session.removeAttribute("username");
            session.removeAttribute("adm");
            session.invalidate();
        }
        catch(IllegalStateException e){
            e.printStackTrace();
        }
        return "welcome-page";
    }
   
    public void redirect () throws IOException
    {
       if(!searchSTR.equals("") ) 
        FacesContext.getCurrentInstance().getExternalContext().redirect("/e-auction-2015/faces/searchSTR:"+searchSTR+"_searchCAT:"+searchCAT);
    }
    
}
