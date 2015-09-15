/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import DAOs.UsersDAO;
import Beans.Usersbean;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import helpers.EmailValidator;
import java.io.Serializable;
import org.primefaces.context.RequestContext;




/**
 *
 * @author George
 */


@ManagedBean (name = "signup_form")
@SessionScoped

public class signup_form implements Serializable{
    
    Usersbean usr=new Usersbean();
    String confirm_pass="";

    public String getConfirm_pass() {
        return confirm_pass;
    }

    public void setConfirm_pass(String confirm_pass) {
        this.confirm_pass = confirm_pass;
    }

    public Usersbean getUsr() {
        return usr;
    }

    public void setUsr(Usersbean usr) {
        this.usr = usr;
    }    
    
    public String submit(String username,String password,String email) {
        EmailValidator emval=new EmailValidator(); 
        RequestContext context = RequestContext.getCurrentInstance();
        usr.setUsername(username);
        usr.setPassword(password);
        usr.setEmail(email);
        if(usr.getUsername().isEmpty() || usr.getPassword().isEmpty() || confirm_pass.isEmpty() || usr.getFirstname().isEmpty() || usr.getLastname().isEmpty() || usr.getEmail().isEmpty() || usr.getPhone().isEmpty() || usr.getAddress_info().isEmpty() || usr.getPostcode().isEmpty() || usr.getVatnumber().isEmpty() ){
            context.execute("PF('fill_in').show();");
            return "";
        }
        else if(!emval.validate(usr.getEmail())){
          context.execute("PF('invalid_email').show();");
          return "";
        }
        else if(!usr.getPassword().equals(confirm_pass)){
          context.execute("PF('Confirm_pass').show();");
          return "";
        }
        UsersDAO bean=new UsersDAO();
        
        if(bean.addUser(usr)){
           context.execute("PF('adduser_error').show();");
           return "";
        }
        else{
           context.execute("PF('Signup_success').show();");
           return "index"; 
        }
    }
   
    public String signup_success_redirection(){
          return "index";
    }
           
           
    
    
}
