/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Beans.Bidsbean;
import DB_Conn.HibernateUtil;
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
    
    //Get items that had been bid form specific user
    public List getmybids_distinct(String bidder){
        List <Integer> bids_itemids=null;
        String hql =   " select DISTINCT(b.bid_itemid)" 
                   +   " from Beans.Bidsbean b"
                   +   " where b.bidder LIKE :qusername";
        Transaction tx = null;    
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           bids_itemids = session.createQuery(hql)
                   .setParameter("qusername", "%" + bidder + "%").list();
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
        return bids_itemids;
    }
    
    //Get users that had bid the item with the specific id
    public List getitem_bidders_distinct(int item_id){
        List <String> bidders=null;
        String hql =   " select DISTINCT(b.bidder)" 
                   +   " from Beans.Bidsbean b"
                   +   " where b.bid_itemid = "+item_id+ "";
        Transaction tx = null;    
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           bidders = session.createQuery(hql).list();
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
        return bidders;
    }
    
    
    //Check if the givven user has bidded the given item in the past.
    public boolean check_bid_existance(int item_id,String bidder){
        int check_bid_existance=0;
        String hql =   " select b" 
                   +   " from Beans.Bidsbean b"
                   +   " where b.bid_itemid = "+item_id+""
                   +   " and b.bidder LIKE :qbidder";
        Transaction tx = null;    
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           check_bid_existance = session.createQuery(hql)
                   .setParameter("qbidder", "%" + bidder + "%").list().size();
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
        
        if(check_bid_existance!=0)
          return true;
        else
          return false;
    }
    
}
