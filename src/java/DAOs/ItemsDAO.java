/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Beans.Itemsbean;
import DB_Conn.HibernateUtil;
import java.util.Date;
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
public class ItemsDAO {
    
    private HibernateUtil helper;
    private Session session;
    
    public int additem(Itemsbean item){
        
        int itemid=-999;
        Transaction tx = null; 
        boolean err=false;   
        item.setPubliced_win(false);
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           session.save(item);
           itemid=item.getItemId();
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
        return itemid;
    }
    
    public boolean updateitem(Itemsbean item){
        
        Transaction tx = null; 
        boolean err=false;   
        item.setPubliced_win(false);
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           session.update(item);
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
           err=true;
           e.printStackTrace();
        }
        return err;
    }
    
    public boolean delete_item(Itemsbean item){
        Transaction tx = null;     
        boolean err=false;  
        try{
           session = helper.getSessionFactory().openSession();
           tx = session.beginTransaction();
           session.delete(item);
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
    
    public Itemsbean getitem(int itemid){
        List <Itemsbean> items=null;
      
        Transaction tx = null;    
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           Criteria cr  = session.createCriteria(Itemsbean.class);
           cr.add(Restrictions.eq("itemId", itemid));
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
        
        if(items==null){
            return null;
        }else if(items.isEmpty())
            return null;
        else
            return items.get(0);
    }
    
    
     
    public List getitems(int itemid){
        List <Itemsbean> items=null;
      
        Transaction tx = null;    
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           Criteria cr  = session.createCriteria(Itemsbean.class);
           cr.add(Restrictions.eq("itemId", itemid));
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
    
    public List getallitems(){
        List <Itemsbean> items=null;
      
        Transaction tx = null;    
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           Criteria cr  = session.createCriteria(Itemsbean.class);
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
    
    public List getitemsBySeller(String Seller,int firstRow, int rowCount){
        List <Itemsbean> items=null;
      
        Transaction tx = null;    
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           Criteria cr  = session.createCriteria(Itemsbean.class);
           cr.add(Restrictions.eq("seller", Seller))
                              .setFirstResult(firstRow)
                              .setMaxResults(rowCount).list();;
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
    
    public int getResultNumber_for_seller_items (String sellername){
        int rowsNum = 0;
        String hql = "select i.itemId from Beans.Itemsbean i where i.seller = :sellername ";
        Transaction tx = null;    
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           rowsNum = session.createQuery(hql).setParameter("sellername", sellername).list().size();
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
        return rowsNum;
    }
    
     public List getitemsByCategory (String cat, String name){
        List <Itemsbean> items=null;
        if(cat.equals("")){
            String hql =   " select DISTINCT(i) "
                   +   " from Beans.Itemsbean i, Beans.Item_has_categorybean ihc, Beans.Categoriesbean c "
                   +   " where i.name LIKE :itemname "
                   +   " and 0 < Timestampdiff(second,NOW(),i.ends)";
            Transaction tx = null;    
            try{
               session = helper.getSessionFactory().openSession();
               tx=session.beginTransaction();
               items = session.createQuery(hql)
                       .setParameter("itemname", "%" + name + "%").list();
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
        }else{
            String hql;
            Transaction tx;
            if(cat.equals("Collectibles & art")||cat.equals("Electronics")||cat.equals("Entertainment")
                                               ||cat.equals("Fashion")||cat.equals("Home & garden")||cat.equals("Motors")
                                               ||cat.equals("Sporting goods")||cat.equals("Toys & hobbies")){
                           hql =   " select DISTINCT(i) "
                               +   " from Beans.Itemsbean i, Beans.Item_has_categorybean ihc, Beans.Categoriesbean c "
                               +   " where i.name LIKE :itemname "
                               +   " and c.parent_category LIKE :catname "
                               +   " and c.categoryId = ihc.categoryId "
                               +   " and ihc.itemId = i.itemId "
                               +   " and 0 < Timestampdiff(second,NOW(),i.ends)";
                           tx = null;
            }else{
                           hql =   " select DISTINCT(i) "
                               +   " from Beans.Itemsbean i, Beans.Item_has_categorybean ihc, Beans.Categoriesbean c "
                               +   " where i.name LIKE :itemname "
                               +   " and c.category LIKE :catname "
                               +   " and c.categoryId = ihc.categoryId "
                               +   " and ihc.itemId = i.itemId "
                               +   " and 0 < Timestampdiff(second,NOW(),i.ends)";
                           tx = null;
            }
            try{
               session = helper.getSessionFactory().openSession();
               tx=session.beginTransaction();
               items = session.createQuery(hql)
                       .setParameter("catname", "%" + cat + "%")
                       .setParameter("itemname", "%" + name + "%").list();
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

        return items;
    }
    
    public int getResultNumber (String cat, String name){
        int rowsNum = 0;
        if(cat.equals("")){
            String hql =   " select DISTINCT(i) "
                   +   " from Beans.Itemsbean i, Beans.Item_has_categorybean ihc, Beans.Categoriesbean c "
                   +   " where i.name LIKE :itemname "
                   +   " and 0 < Timestampdiff(second,NOW(),i.ends)";
            Transaction tx = null;    
            try{
               session = helper.getSessionFactory().openSession();
               tx=session.beginTransaction();
               rowsNum = session.createQuery(hql)
                       .setParameter("itemname", "%" + name + "%").list().size();
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
        }else{
             String hql;
            Transaction tx;
            if(cat.equals("Collectibles & art")||cat.equals("Electronics")||cat.equals("Entertainment")
                                               ||cat.equals("Fashion")||cat.equals("Home & garden")||cat.equals("Motors")
                                               ||cat.equals("Sporting goods")||cat.equals("Toys & hobbies")){
                           hql =   " select DISTINCT(i) "
                               +   " from Beans.Itemsbean i, Beans.Item_has_categorybean ihc, Beans.Categoriesbean c "
                               +   " where i.name LIKE :itemname "
                               +   " and c.parent_category LIKE :catname "
                               +   " and c.categoryId = ihc.categoryId "
                               +   " and ihc.itemId = i.itemId "
                               +   " and 0 < Timestampdiff(second,NOW(),i.ends)";
                           tx = null;
            }else{
                           hql =   " select DISTINCT(i) "
                               +   " from Beans.Itemsbean i, Beans.Item_has_categorybean ihc, Beans.Categoriesbean c "
                               +   " where i.name LIKE :itemname "
                               +   " and c.category LIKE :catname "
                               +   " and c.categoryId = ihc.categoryId "
                               +   " and ihc.itemId = i.itemId "
                               +   " and 0 < Timestampdiff(second,NOW(),i.ends)";
                           tx = null;
            }
            try{
               session = helper.getSessionFactory().openSession();
               tx=session.beginTransaction();
               rowsNum = session.createQuery(hql)
                       .setParameter("catname", "%" + cat + "%")
                       .setParameter("itemname", "%" + name + "%").list().size();
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
        return rowsNum;

    }
    
    
    public List getbiditems (String username,int firstRow, int rowCount){
        List <Itemsbean> items=null;
  
        String hql =   " select DISTINCT(i) "
                   +   " from Beans.Itemsbean i, Beans.Bidsbean bids "
                   +   " where bids.bid_itemid=i.itemId "
                   +   " and bids.bidder LIKE :qusername";
        Transaction tx = null;    
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           items = session.createQuery(hql)
                   .setParameter("qusername", "%" + username + "%")
                   .setFirstResult(firstRow)
                   .setMaxResults(rowCount).list();
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
    
    public int getResultNumber_of_bid (String username){
        int rowsNum = 0;
        String hql =   " select DISTINCT(i) "
                   +   " from Beans.Itemsbean i, Beans.Bidsbean bids "
                   +   " where bids.bid_itemid=i.itemId "
                   +   " and bids.bidder LIKE :qusername";
        Transaction tx = null;    
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           rowsNum = session.createQuery(hql)
                   .setParameter("qusername", "%" + username + "%").list().size();
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
        
        return rowsNum;

    }
    
    
    public boolean addbid(int itemid,int bid,String bider_username){
        
        List <Itemsbean> items=null;
      
        Transaction tx = null;    
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           Criteria cr  = session.createCriteria(Itemsbean.class);
           cr.add(Restrictions.eq("itemId", itemid));
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
        
       
        if(items.get(0).getCurrently()>=bid)
           return false;
        Date date=new Date();
        long seconds = (items.get(0).getEnds().getTime() - date.getTime())/1000;
        if(seconds<0)//This means that the item is not for sale any more
            return false;
        
        items.get(0).setCurrently(bid);
        items.get(0).setWinner(bider_username);
        items.get(0).setNumber_of_bids(items.get(0).getNumber_of_bids()+1);
        
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           session.update(items.get(0));
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
        
        return true;
    }
    
    
    public boolean buyitem(int itemid,String buyer_username){
        
        List <Itemsbean> items=null;
        Transaction tx = null;    
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           Criteria cr  = session.createCriteria(Itemsbean.class);
           cr.add(Restrictions.eq("itemId", itemid));
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
        
        Date date=new Date();
        long seconds = (items.get(0).getEnds().getTime() - date.getTime())/1000;
        if(seconds<0)//This means that the item is not for sale any more
            return false;
        
        items.get(0).setWinner(buyer_username);
        items.get(0).setEnds(date);
        items.get(0).setCurrently(items.get(0).getBuy_price());
        
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           session.update(items.get(0));
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
        
        
        return true;
    }
    
    public boolean set_started_ends_dates(int itemid,Date expiry_date,Date start_date){
        List <Itemsbean> items=null;
        Transaction tx = null;  
        boolean err=false;
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           Criteria cr  = session.createCriteria(Itemsbean.class);
           cr.add(Restrictions.eq("itemId", itemid));
           items=cr.list();
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
           err=true;
        }
        
        if(items==null || items.isEmpty())
            return true;
            
        items.get(0).setStarted(start_date);
        items.get(0).setEnds(expiry_date);
        items.get(0).setPubliced_win(false);
        
        
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           session.update(items.get(0));
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
           err=true;
        }
        
        return err;
        
    }
    
    
     public boolean publice_win (String username){
        List <Itemsbean> items=null;
        boolean err=false;
        String hql =   " select DISTINCT(i) "
                   +   " from Beans.Itemsbean i"
                   +   " where i.publiced_win = 0"
                   +   " and i.winner LIKE :qusername"
                   +   " and 0 > Timestampdiff(second,NOW(),i.ends)";
        Transaction tx = null;    
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           items = session.createQuery(hql)
                   .setParameter("qusername", "%" + username + "%").list();
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
           err=true;
           e.printStackTrace();
        }
        

        if(items==null || items.isEmpty())
            return false;
        items.get(0).setPubliced_win(true);
        
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           session.update(items.get(0));
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
           err=true;
        }
        if(err)
            return false;
        MessagesDAO messagedao=new MessagesDAO();
        messagedao.savemessage(items.get(0).getSeller(), username, "Hello, I just  win the auction for your product with name " +items.get(0).getName()+ " for "+items.get(0).getCurrently()+" euros.Please contact with me for the next steps.");
        messagedao.savemessage(username, username, "Congratulations!!! You won the auction for the item " +items.get(0).getName()+ " for "+items.get(0).getCurrently()+" euros.A message has been sent to inform the seller.");
        
        return true;
    }
    
}





