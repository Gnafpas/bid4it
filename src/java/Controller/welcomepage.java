package Controller;


import DAOs.UsersDAO;
import Beans.Usersbean;
import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import helpers.EmailValidator;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import java.io.Serializable;




/**
 *
 * @author George
 */


@ManagedBean (name = "welcomepage")
@SessionScoped

public class welcomepage implements Serializable {
    
    public String su_username="";
    public String su_password="";
    public String su_email="";
    public String lg_username="";
    public String lg_password="";

    public String getLg_username() {
        return lg_username;
    }

    public void setLg_username(String lg_username) {
        this.lg_username = lg_username;
    }

    public String getLg_password() {
        return lg_password;
    }

    public void setLg_password(String lg_password) {
        this.lg_password = lg_password;
    }

    public String getSu_username() {
        return su_username;
    }

    public void setSu_username(String su_username) {
        this.su_username = su_username;
    }

    public String getSu_password() {
        return su_password;
    }

    public void setSu_password(String su_password) {
        this.su_password = su_password;
    }

    public String getSu_email() {
        return su_email;
    }

    public void setSu_email(String su_email) {
        this.su_email = su_email;
    }

    public String redirection_to_signup_form() throws IOException {
       RequestContext context = RequestContext.getCurrentInstance();
       EmailValidator emval=new EmailValidator(); 
       if(su_username.isEmpty() || su_password.isEmpty() || su_email.isEmpty()){
          context.execute("PF('fill_in').show();");
          return "";       
       }
       else if(!emval.validate(su_email)){
          context.execute("PF('invalid_email').show();");
          return "";
       }
       else
          return "signup-form";
    }
    
    public String login() throws IOException {
        RequestContext context = RequestContext.getCurrentInstance();
        if(lg_username.isEmpty() || lg_password.isEmpty()){
            context.execute("PF('fill_in').show();");
            return "";
        }
        UsersDAO bean=new UsersDAO();
        Usersbean usr= bean.getUser(lg_username);
        if(usr==null){
            context.execute("PF('Please_Signup').show();");
            return "";
        }
        else if(usr.getPassword().equals(lg_password)){
            if(usr.isAdm()){
                HttpSession session = SessionBean.getSession();
                session.setAttribute("username", lg_username);
                session.setAttribute("adm", "true");
                return "admin";
            }
            else if(usr.isPending_state()){
                context.execute("PF('Pending_state_alert').show();");
                return "";
            }
            else{
                HttpSession session = SessionBean.getSession();
                session.setAttribute("username", lg_username);
                session.setAttribute("adm", "false");
                return "index";
            }
        }
        else{
            context.execute("PF('Wrong_Password').show();");
            return "";
        }
           
    }
    
    public String login_pendinguser_redirection(){
          return "index";
    }

     
  
}
