/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Beans.Item_has_categorybean;
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
public class Item_has_categoryDAO {
    
    private HibernateUtil helper;
    private Session session;
    
    public boolean addcategory_to_item(Item_has_categorybean cat){
        
        Transaction tx = null; 
        boolean err=false;    
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           session.save(cat);
           tx.commit();
           session.close(); 
        }catch (HibernateException e) {
           RequestContext context = RequestContext.getCurrentInstance();
           context.execute("PF('server_error').show();");
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
    
     public List getcategory_from_item(int itemid){
        List <Item_has_categorybean> categories=null;
      
        Transaction tx = null;    
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           Criteria cr  = session.createCriteria(Item_has_categorybean.class);
           cr.add(Restrictions.eq("itemId", itemid));
           categories=cr.list();
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
        return categories;
    }
     
    public List getitems_from_categoryid(int categoryid){
        List <Item_has_categorybean> items=null;
      
        Transaction tx = null;    
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           Criteria cr  = session.createCriteria(Item_has_categorybean.class);
           cr.add(Restrictions.eq("categoryId", categoryid));
           items=cr.list();
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
        return items;
    }
    
}
