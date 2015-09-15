/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Beans.Categoriesbean;
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
public class CategoriesDAO {

    private HibernateUtil helper;
    private Session session;
    
     //Gets category name and returns category id.
     public int getCategoryId(String category_name){
        List <Categoriesbean> cat=null;
        Transaction tx = null;    
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           Criteria cr  = session.createCriteria(Categoriesbean.class);
           cr.add(Restrictions.eq("category", category_name));
           cat=cr.list();
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
        if(cat.isEmpty())
          return 0;  
        else
          return cat.get(0).getCategoryId();
    }
    
    //Gets category id  and returns category.
     public Categoriesbean getCategory(int category_id){
        List <Categoriesbean> cat=null;
        Transaction tx = null;    
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           Criteria cr  = session.createCriteria(Categoriesbean.class);
           cr.add(Restrictions.eq("categoryId", category_id));
           cat=cr.list();
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
        if(cat.isEmpty())
          return null;  
        else
          return cat.get(0);
        
    }
}
