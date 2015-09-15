/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;


import Beans.Usersbean;
import DB_Conn.HibernateUtil;
import java.io.IOException;
import static java.lang.System.out;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.primefaces.context.RequestContext;

/**
 *
 * @author adonis
 */

public  class UsersDAO {

    private HibernateUtil helper;
    private Session session;
    private List<Usersbean > allActiveUsers  = null;
    private List<Usersbean > allPendingUsers  = null;
    
    public boolean addUser(Usersbean user)  {
        boolean err=false;
        user.setPending_state(true);
        user.setAdm(false);
        Transaction tx = null; 
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           session.save(user);
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
           err=true;
        }
        return err;
     }

  
    public Usersbean getUser(String key)  {
        Usersbean user=null;
        Transaction tx = null;
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           user=(Usersbean) session.get(Usersbean.class, key);
           session.close();
        }catch (ExceptionInInitializerError e) {
           RequestContext context = RequestContext.getCurrentInstance();
           context.execute("PF('server_error').show();");
           e.printStackTrace();
        }catch (HibernateException e) {
           RequestContext context = RequestContext.getCurrentInstance();
           context.execute("PF('server_error').show();");
           e.printStackTrace();
           
        }
        return user;
    }
   
    
    public List getAllActiveUsers( )  {
        Usersbean user=null;    
        Transaction tx = null;        

        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           Criteria cr  = session.createCriteria(Usersbean.class);
           cr.add(Restrictions.eq("pending_state", false));
           allActiveUsers =  cr.list();
           session.close();
        }catch (ExceptionInInitializerError e) {
           RequestContext context = RequestContext.getCurrentInstance();
           context.execute("PF('server_error').show();");
           e.printStackTrace();
        }catch (HibernateException e) {
           RequestContext context = RequestContext.getCurrentInstance();
           context.execute("PF('server_error').show();");
           e.printStackTrace();
           session.close();
        }
        return allActiveUsers;
    }
    
    public List getAllPendingUsers( )  {
        Usersbean user=null;
        Boolean key = true;
        Transaction tx = null;        
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           Criteria cr  = session.createCriteria(Usersbean.class);
           cr.add(Restrictions.eq("pending_state", true));
           allPendingUsers =  cr.list();
           session.close();
        }catch (ExceptionInInitializerError e) {
           RequestContext context = RequestContext.getCurrentInstance();
           context.execute("PF('server_error').show();");
           e.printStackTrace();
        }catch (HibernateException e) {
           RequestContext context = RequestContext.getCurrentInstance();
           context.execute("PF('server_error').show();");
           e.printStackTrace();
           session.close();
        }
        return allPendingUsers;
    }
    
    public int acceptUser(String key ) throws IOException {
        Usersbean user=null;
        Transaction tx = null;  
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           user=(Usersbean) session.get(Usersbean.class, key);
           user.setPending_state(false);
           session.update(user);
           tx.commit();
           session.close();
        }catch (ExceptionInInitializerError e) {
            e.printStackTrace();
            return 1;
        }catch (HibernateException e) {
            e.printStackTrace();
            session.close();
            return 1;
        }
         return 0;
    }
    
    public int deleteUser(String key ) throws IOException 
    {
        Usersbean user=null;
        int flag=0;
        Transaction tx = null;  
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           user=(Usersbean) session.get(Usersbean.class, key);
           if (!user.isAdm())
           {    session.delete(user);
                tx.commit();
           }
           else
           {   flag = 2;
           }
           session.close();
        }catch (ExceptionInInitializerError e) {
            e.printStackTrace();
            return 1;
        }catch (HibernateException e) {
            session.close();
            return 1;
        }
        return flag;
    }
}
 