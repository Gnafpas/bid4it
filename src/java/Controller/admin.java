/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAOs.UsersDAO;
import Beans.Usersbean;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.mail.*;
import javax.mail.internet.*;
import org.primefaces.context.RequestContext;

@ManagedBean (name = "admin")
@SessionScoped
public class admin implements Serializable{
    Usersbean usr = new Usersbean();
    Usersbean usr2 = new Usersbean();
    private String msg ;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    
    public Usersbean getUsr() {
        return usr;
    }
    
    public void setUsr(Usersbean user) {
        this.usr = user;
    }
    public Usersbean getUsr2() {
        return usr2;
    }
    
    public void setUsr2(Usersbean user) {
        this.usr2 = user;
    }
    public List<Usersbean > getAllActiveUsers() throws IOException {

        UsersDAO bean=new UsersDAO();
        List<Usersbean > allActiveUsers = bean.getAllActiveUsers();
  
        
        return allActiveUsers;
    }
   
    public List<Usersbean > getAllPendingUsers() throws IOException {

        UsersDAO bean=new UsersDAO();
        List<Usersbean > allPendingUsers = bean.getAllPendingUsers();
  
        
        return allPendingUsers;
    }
    
    public Usersbean  getUser(String key ) throws IOException {

        UsersDAO bean=new UsersDAO();
        Usersbean user = bean.getUser(key);
        if (user.isPending_state())
        {   usr=user;
            return usr;
        }
        else  
        {
            usr2 = user;
            return usr2;
        }
    }
    
    public int acceptUser(String key ) throws IOException 
    {   UsersDAO bean=new UsersDAO();
        if ((bean.acceptUser(key))==0)
        {
            return 0;
        }
        usr = null;
        return 1;  
    }
    
    public int deleteUser(String key ) throws IOException 
    {   UsersDAO bean=new UsersDAO();
         RequestContext context = RequestContext.getCurrentInstance();
         int temp = bean.deleteUser(key); 
        if (temp == 0)
        {
            usr=null;
            context.execute("PF('user_deleted').show();");
            return 0;
        }
        else if (temp == 2 )
        {    context.execute("PF('user_is_admin').show();");
        
        }
        return 1;  
    }
    
/**
 *
 * Sends email from admin to users for accept/reject registration or delete user.
 */
    public void sendEmail(String to)
    {

      final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
      String from = "bid4it.gr@gmail.com";
      String host = "smtp.gmail.com";
      Properties properties = System.getProperties();
      properties.setProperty("mail.smtp.host", host);
      properties.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
      properties.setProperty("mail.smtp.socketFactory.fallback", "false");
      properties.setProperty("mail.smtp.port", "587");
      properties.setProperty("mail.smtp.socketFactory.port", "587");
      properties.setProperty("mail.smtps.auth", "true");
      properties.setProperty("mail.smtp.starttls.enable", "true");
      Session session = Session.getDefaultInstance(properties, null);

      try{

         MimeMessage message = new MimeMessage(session);
         message.setFrom(new InternetAddress(from));
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
         message.setSubject("This is the Subject Line!");
         message.setText(msg);
         // Send message
         Transport transport = session.getTransport("smtp");
         transport.connect(host,from,"bid4itgr");
        transport.sendMessage(message, message.getAllRecipients());
         System.out.println("Sent message successfully...." + to);
      }catch (MessagingException mex) {
         mex.printStackTrace();
      }
    }
}