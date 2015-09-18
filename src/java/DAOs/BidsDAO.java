/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Beans.Bidsbean;
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
public class BidsDAO {
    
    private HibernateUtil helper;
    private Session session;
    
    public boolean addbid(Bidsbean bid){
        
        Transaction tx = null; 
        boolean err=false;    
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           session.save(bid);
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
        return err;
    }
    
    public List getmybids(String bidder){
        List <Bidsbean> bids=null;
      
        Transaction tx = null;    
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           Criteria cr  = session.createCriteria(Bidsbean.class);
           cr.add(Restrictions.eq("bidder", bidder));
           bids=cr.list();
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
        return bids;
    }
    
    public List getitemsbids(int itemid){
        List <Bidsbean> bids=null;
      
        Transaction tx = null;    
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           Criteria cr  = session.createCriteria(Bidsbean.class);
           cr.add(Restrictions.eq("bid_itemid", itemid));
           bids=cr.list();
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
        return bids;
    }
    
}
