/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Beans.Messagesbean;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import DAOs.MessagesDAO;
import java.io.Serializable;
import static java.lang.System.out;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author George
 */
@ManagedBean (name = "messages")
@SessionScoped

public class messages implements Serializable{
    boolean all_messages=true;
    boolean unread_messages=false;
    boolean sent_messages=false;
    private String receiver="";
    private String message_to_send="";
    private String message_to_read="";
    
    private List<Messagesbean> allMessages  = new ArrayList();
    private List<Messagesbean> unreadMessages  = new ArrayList();
    private List<Messagesbean> sentMessages  = new ArrayList();
    private Messagesbean message=new Messagesbean();


    
    
    public List<Messagesbean> getAllMessages() {
        return allMessages;
    }

    public void setAllMessages(List<Messagesbean> allMessages) {
        this.allMessages = allMessages;
    }

    public List<Messagesbean> getUnreadMessages() {
        return unreadMessages;
    }

    public void setUnreadMessages(List<Messagesbean> viewedMessages) {
        this.unreadMessages = viewedMessages;
    }

    public List<Messagesbean> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(List<Messagesbean> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public Messagesbean getMessage() {
        return message;
    }

    public void setMessage(Messagesbean message) {
        this.message = message;
    }

    public String getMessage_to_send() {
        return message_to_send;
    }

    public void setMessage_to_send(String message_to_send) {
        this.message_to_send = message_to_send;
    }

    
    
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    
    
    public boolean isAll_messages() {
        return all_messages;
    }

 /**
 *
 * Functions for navigating into messages environment.
 */
    
    public String setAll_messages(boolean all_messages) {
        this.all_messages = all_messages;
        sent_messages=false;
        unread_messages=false;
        return "messages";
    }

    public boolean isUnread_messages() {
        return unread_messages;
    }

    public String setUnread_messages(boolean unread_messages) {
        this.unread_messages = unread_messages;
        sent_messages=false;
        all_messages=false;
        return "messages";
    }

    public boolean isSent_messages() {
        return sent_messages;
    }

    public String setSent_messages(boolean sent_messages) {
        this.sent_messages = sent_messages;
        all_messages=false;
        unread_messages=false;
        return "messages";
    }


    public String setNew_message(boolean new_message) {
        return "write_msg";
    }


    
 /**
 *
 * Save a message as read.
 */
    
    public String getMessage_to_read() {
        return message_to_read;
    }

    public String setMessage_to_read(int msgid) {
         for (Messagesbean msg : allMessages) {
             if (msg.getMessageId()==msgid) {
                message_to_read=msg.getMessage();
                if(msg.isChecked()==false){
                   MessagesDAO bean=new MessagesDAO();
                   bean.setmessage_checked(msg);
                }
             }
          }
          for (Messagesbean msg : sentMessages) {
             if (msg.getMessageId()==msgid) {
                message_to_read=msg.getMessage();
             }
          }
        
          return "read_msg";
    }

    
    
    
    public void sendmessage() {
        RequestContext context = RequestContext.getCurrentInstance();  
        MessagesDAO bean=new MessagesDAO();
          if(bean.savemessage(receiver, SessionBean.getUserName(), message_to_send)){
              context.execute("PF('sendmsg_error').show();");
          }
          else
              context.execute("PF('sendmsg').show();");
    }
    
    public void get_All_messages() {  
        MessagesDAO bean=new MessagesDAO();
        allMessages= bean.getmessages(SessionBean.getUserName(), null, false);
    }

    public void get_unread_messages() {
        unreadMessages  = new ArrayList();
        if(!allMessages.isEmpty()){
          for (Messagesbean msg : allMessages) {
             if (!msg.isChecked()) {
                unreadMessages.add(msg);
             }
          }
        }
    }
    
    public void get_sent_messages() {  
        MessagesDAO bean=new MessagesDAO();
        sentMessages= bean.getmessages(null, SessionBean.getUserName(), false);
    }

/**
 *
 * Informs user with notification at navbar if he has unread messages.
 */
    public boolean newmessages(){
        if(unreadMessages.isEmpty())
            return false;
        else
            return true;
    }
    
    public void delete_msg(Messagesbean msg){
       RequestContext context = RequestContext.getCurrentInstance();
       MessagesDAO bean=new MessagesDAO();
       if(bean.delete_message(msg))
           context.execute("PF('deltemsg_error').show();");
       else
           context.execute("PF('deltemsg').show();");
    }
           
}
