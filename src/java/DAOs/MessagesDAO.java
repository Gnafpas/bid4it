/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Beans.Messagesbean;
import DB_Conn.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.primefaces.context.RequestContext;

/**
 *
 * @author George
 */
public class MessagesDAO {
    
    private HibernateUtil helper;
    private Session session;

 
    public boolean savemessage(String receiver,String sender,String text){
        Messagesbean msg=new Messagesbean();
        msg.setReceiver(receiver);
        msg.setMessage(text);
        msg.setSender(sender);
        msg.setChecked(false);
        Transaction tx = null; 
        boolean err=false;    
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           session.save(msg);
           tx.commit();
           session.close(); 
        }catch (HibernateException e) {
           e.printStackTrace(); 
           err=true;
           session.close(); 
        }catch (ExceptionInInitializerError e) {
           RequestContext context = RequestContext.getCurrentInstance();
           context.execute("PF('server_error').show();");
           e.printStackTrace();
        }
        return err;
    }
    
    
    public List getmessages(String receiver,String sender,boolean onlyunread){
        List <Messagesbean> messages=null;
        Transaction tx = null;     
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           Criteria cr  = session.createCriteria(Messagesbean.class);
           if(receiver!=null)
             cr.add(Restrictions.eq("receiver", receiver));
           if(sender!=null)
             cr.add(Restrictions.eq("sender", sender));
           if(onlyunread)
             cr.add(Restrictions.eq("viewed", false));
           messages =  cr.list();
           session.close(); 
        }catch (HibernateException e) {
           RequestContext context = RequestContext.getCurrentInstance();
           context.execute("PF('server_error').show();");
           e.printStackTrace();
           session.close(); 
        }catch (ExceptionInInitializerError e) {
           RequestContext context = RequestContext.getCurrentInstance();
           context.execute("PF('server_error').show();");
           e.printStackTrace();
        }
        return messages;
    }
    
    public boolean delete_message(Messagesbean msg){
        Transaction tx = null;     
        boolean err=false;  
        try{
           session = helper.getSessionFactory().openSession();
           tx = session.beginTransaction();
           session.delete(msg);
           tx.commit();
           session.close();
        }catch (HibernateException e) {
           e.printStackTrace(); 
           err=true;
           session.close();
        }catch (ExceptionInInitializerError e) {
           RequestContext context = RequestContext.getCurrentInstance();
           context.execute("PF('server_error').show();");
           e.printStackTrace();
        }
        return err;
    }
    
    public void setmessage_checked(Messagesbean msg){
        
        Transaction tx = null;     
        try{
           session = helper.getSessionFactory().openSession();
           tx = session.beginTransaction();
           msg.setChecked(true);
           session.update(msg);
           tx.commit();
           session.close(); 
        }catch (HibernateException e) {
           RequestContext context = RequestContext.getCurrentInstance();
           context.execute("PF('server_error').show();");
           e.printStackTrace(); 
           session.close(); 
        }catch (ExceptionInInitializerError e) {
           RequestContext context = RequestContext.getCurrentInstance();
           context.execute("PF('server_error').show();");
           e.printStackTrace();
        }
       
    }

}
