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
import DAOs.ItemsDAO;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author George
 */
@ManagedBean (name = "headerbean")
@SessionScoped

public class header implements Serializable{
    private Timer timer = new Timer();
    Usersbean  user=null;
    String searchSTR;
    String searchCAT;
    private ItemsDAO it= new ItemsDAO();
    final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    
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
            //Timer for seeing if user won any auction every 30 seconds.If he won sends notification mail to himself and to seller.
            service.scheduleWithFixedDelay(new Runnable()
              {
                @Override
                public void run()
                {
                // out.println(new Date());
                 it.publice_win(user.getUsername());
                }
              }, 0, 30, TimeUnit.SECONDS);
      
            return  true;
       }
    }
    
    public String logout(){
        HttpSession session = SessionBean.getSession();
        try{
            service.shutdown();
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
