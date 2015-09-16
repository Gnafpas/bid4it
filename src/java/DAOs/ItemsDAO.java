/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import Beans.Itemsbean;
import DB_Conn.HibernateUtil;
import static java.lang.System.out;
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
    
    public List getitemsByCategory (String cat, String name, int firstRow, int rowCount){
        List <Itemsbean> items=null;
        String hql = "select DISTINCT(i) from Beans.Itemsbean i, Beans.Item_has_categorybean ihc, Beans.Categoriesbean c where i.name LIKE :itemname and c.category LIKE :catname and c.categoryId = ihc.categoryId and ihc.itemId = i.itemId ";
        Transaction tx = null;    
        try{
           session = helper.getSessionFactory().openSession();
           tx=session.beginTransaction();
           items = session.createQuery(hql)
                   .setParameter("catname", "%" + cat + "%")
                   .setParameter("itemname", "%" + name + "%")
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
    
    public int getResultNumber (String cat, String name){
        int rowsNum = 0;
        String hql = "select DISTINCT(i) from Beans.Itemsbean i, Beans.Item_has_categorybean ihc, Beans.Categoriesbean c where i.name LIKE :itemname and c.category LIKE :catname and c.categoryId = ihc.categoryId and ihc.itemId = i.itemId ";
        Transaction tx = null;    
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
        
        MessagesDAO messagedao=new MessagesDAO();
        messagedao.savemessage(items.get(0).getSeller(), buyer_username, buyer_username + " bought your product with name " +items.get(0).getName()+ ".Please send him a message for the next steps.");
        
        return true;
    }
}





