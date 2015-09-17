/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Beans.Itemsbean;
import DAOs.Item_has_imageDAO;
import DAOs.ItemsDAO;
import helpers.Custom_date;
import helpers.Seller_items;
import java.io.FileOutputStream;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author George
 */

@ManagedBean (name = "selling")
@SessionScoped
public class selling {
    
    
    Itemsbean item = new Itemsbean();
    private List <Seller_items> pageItems = new ArrayList();
    private String main_image="search_images/";
    private int totalRows;
    private int firstRow;
    private int rowsPerPage;
    private int totalPages;
    private int pageRange;
    private Integer[] pages;
    private int currentPage;
    private String Active_with_bids="Active_with_bids";
    private String Active_without_bids="Active_without_bids";
    private String Completed_and_bought="Completed_and_bought";
    private String Completed_without_bought="Completed_without_bought";
    private String Not_Active="Not_Active";
    private Date expiry_date=new Date();
    private boolean no_items_toShow=false;

    public boolean isNo_items_toShow() {
        return no_items_toShow;
    }

    public void setNo_items_toShow(boolean no_items_toShow) {
        this.no_items_toShow = no_items_toShow;
    } 

    public Date getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(Date expiry_date) {
        this.expiry_date = expiry_date;
    }
    
    public String getActive_with_bids() {
        return Active_with_bids;
    }

    public void setActive_with_bids(String Active_with_bids) {
        this.Active_with_bids = Active_with_bids;
    }

    public String getActive_without_bids() {
        return Active_without_bids;
    }

    public void setActive_without_bids(String Active_without_bids) {
        this.Active_without_bids = Active_without_bids;
    }

    public String getCompleted_and_bought() {
        return Completed_and_bought;
    }

    public void setCompleted_and_bought(String Completed_and_bought) {
        this.Completed_and_bought = Completed_and_bought;
    }

    public String getCompleted_without_bought() {
        return Completed_without_bought;
    }

    public void setCompleted_without_bought(String Completed_without_bought) {
        this.Completed_without_bought = Completed_without_bought;
    }

    public String getNot_Active() {
        return Not_Active;
    }

    public void setNot_Active(String Not_Active) {
        this.Not_Active = Not_Active;
    }

    public List<Seller_items> getPageItems() {
        return pageItems;
    }

    public void setPageItems(List<Seller_items> pageItems) {
        this.pageItems = pageItems;
    }

    public Itemsbean getItem() {
        return item;
    }

    public void setItem(Itemsbean item) {
        this.item = item;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(int firstRow) {
        this.firstRow = firstRow;
    }

    public int getRowsPerPage() {
        return rowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageRange() {
        return pageRange;
    }

    public void setPageRange(int pageRange) {
        this.pageRange = pageRange;
    }

    public Integer[] getPages() {
        return pages;
    }

    public void setPages(Integer[] pages) {
        this.pages = pages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public String getMain_image() {
        return main_image;
    }

    public void setMain_image(String main_image) {
        this.main_image = main_image;
    }

    
    public void searchmyItems()
    {
        no_items_toShow=false;
        List <Itemsbean> pageItems_whithout_status = new ArrayList();
        ItemsDAO bean=new ItemsDAO();
        
        if (rowsPerPage==0)
            rowsPerPage = 2;
        
        pageRange = 10; // Default page range (max amount of page links to be displayed at once).

        String seller=SessionBean.getUserName();
        if(seller==null)
            return;
        pageItems_whithout_status = bean.getitemsBySeller(seller, firstRow, rowsPerPage);
        if(pageItems_whithout_status==null)
            return;
        if(pageItems_whithout_status.isEmpty()){
            no_items_toShow=true;
            return;
        }
        totalRows = bean.getResultNumber_for_seller_items(seller);
        // Set currentPage, totalPages and pages.
        currentPage = (totalRows / rowsPerPage) - ((totalRows - firstRow) / rowsPerPage) + 1;
        totalPages = (totalRows / rowsPerPage) + ((totalRows % rowsPerPage != 0) ? 1 : 0);
        int pagesLength = Math.min(pageRange, totalPages);
        pages = new Integer[pagesLength];

        // firstPage must be greater than 0 and lesser than totalPages-pageLength.
        int firstPage = Math.min(Math.max(0, currentPage - (pageRange / 2)), totalPages - pagesLength);

        // Create pages (page numbers for page links).
        for (int i = 0; i < pagesLength; i++) {
            pages[i] = ++firstPage;
            
        }
        
        
               
        Item_has_imageDAO ihi=new Item_has_imageDAO();
        pageItems.clear();
        Date date=new Date();
        long seconds;
        for (Itemsbean it : pageItems_whithout_status) {
           Seller_items items_with_status=new Seller_items();
           items_with_status.setItem(it);
           if(it.getEnds()!=null){
               seconds = (it.getEnds().getTime() - date.getTime())/1000;
               if(seconds>0 && it.getWinner()!=null)
                   items_with_status.setStatus("Active_with_bids");
               else if(seconds>0 && it.getWinner()==null)
                   items_with_status.setStatus("Active_without_bids");
               else if(seconds<=0 && it.getWinner()!=null)
                   items_with_status.setStatus("Completed_and_bought");
               else if(seconds<=0 && it.getWinner()==null)
                   items_with_status.setStatus("Completed_without_bought");
            }
            else
               items_with_status.setStatus("Not_Active");
           

            byte[] image;
            image=ihi.getimage(it.getItemId());
            if(image!=null)
            {
                items_with_status.setHas_image(true);
                try{
                    FileOutputStream fos = new FileOutputStream("/Users/George/Desktop/TED/e-auction-2015/web/search_images/"+it.getItemId()+".jpg"); 
                    fos.write(image);
                    fos.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else
                items_with_status.setHas_image(false);
           pageItems.add(items_with_status);
        }
        
        //Set date for starting items
        expiry_date=new Date();
        expiry_date.setMonth(expiry_date.getMonth()+1);
        expiry_date.setYear(expiry_date.getYear()+1900);
    }
    
   // Paging actions -----------------------------------------------------------------------------
    private void page(int firstRow) {
        this.firstRow = firstRow;
        searchmyItems();
    }
    public void pageFirst(ValueChangeEvent event) {
        page(0);
    }
 
    public void pageNext() {
        page(firstRow + rowsPerPage);
    }
 
    public void pagePrevious() {
        page(firstRow - rowsPerPage);
    }
 
    public void pageLast() {
        page(totalRows - ((totalRows % rowsPerPage != 0) ? totalRows % rowsPerPage : rowsPerPage));
    }
 
    public void page(ActionEvent event) {        
        page(((Integer) ((UICommand) event.getComponent()).getValue() - 1) * rowsPerPage);
    }
    
    
    //Start,Remove button actions ---------------------------------------------------
    public void remove(Itemsbean item){
        RequestContext requestContext = RequestContext.getCurrentInstance();
        ItemsDAO it=new ItemsDAO();
        if(!it.delete_item(item))
                  requestContext.execute("PF('delete_item_success').show();");
    }
    
    public void start(int itemid){
            
            long seconds;
            RequestContext requestContext = RequestContext.getCurrentInstance();
            Date date_start=new Date();
            date_start.setYear(date_start.getYear());
            expiry_date.setYear(expiry_date.getYear()-1900);
            expiry_date.setMonth(expiry_date.getMonth()-1);
            seconds = (expiry_date.getTime() - date_start.getTime())/1000;
            if(seconds<0){
               requestContext.execute("PF('Unsuccesful_item_start').show();");
            }
            else{
                ItemsDAO it=new ItemsDAO();
                if(!it.set_started_ends_dates(itemid,expiry_date, date_start ))
                  requestContext.execute("PF('Succesful_item_start').show();");
            }
            
    }
    
    public String rediretion_to_update_item(){
        return "update_item";
    }
    

 
    
}
