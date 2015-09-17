/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Beans.Bidsbean;
import Beans.Item_has_imagebean;
import Beans.Itemsbean;
import DAOs.BidsDAO;
import DAOs.Item_has_imageDAO;
import DAOs.ItemsDAO;
import helpers.Mybids_items;
import helpers.Relateditems;
import java.io.FileOutputStream;
import java.io.Serializable;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
 

/**
 *
 * @author adonis
 */
@ManagedBean (name = "mybids")
@SessionScoped
public class mybids implements Serializable{
    
    Itemsbean item = new Itemsbean();
    private List <Itemsbean> pageItems = new ArrayList();
    private List <Bidsbean> pageBids = new ArrayList();
    private List <Mybids_items> mybids_pageItems = new ArrayList();
    private String main_image="search_images/";
    private int totalRows;
    private int firstRow;
    private int rowsPerPage;
    private int totalPages;
    private int pageRange;
    private Integer[] pages;
    private int currentPage;
    private boolean no_items_toShow=false;

    public boolean isNo_items_toShow() {
        return no_items_toShow;
    }

    public void setNo_items_toShow(boolean no_items_toShow) {
        this.no_items_toShow = no_items_toShow;
    } 

    public List<Itemsbean> getPageItems() {
        return pageItems;
    }

    public void setPageItems(List<Itemsbean> pageItems) {
        this.pageItems = pageItems;
    }

    public List<Mybids_items> getMybids_pageItems() {
        return mybids_pageItems;
    }

    public void setMybids_pageItems(List<Mybids_items> mybids_pageItems) {
        this.mybids_pageItems = mybids_pageItems;
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
    
    public List<Mybids_items > searchItems()
    {
        no_items_toShow=false;
        ItemsDAO bean=new ItemsDAO();
        BidsDAO  bidsdao=new BidsDAO();
        if (rowsPerPage==0)
            rowsPerPage = 2;
        pageRange = 10; // Default page range (max amount of page links to be displayed at once).
            if(SessionBean.getUserName()==null)
                return null;
            pageItems = bean.getbiditems (SessionBean.getUserName(), firstRow, rowsPerPage);
            pageBids =bidsdao.getmybids(SessionBean.getUserName());
            if(pageItems==null || pageBids==null)
                return null;
            if(pageItems.isEmpty() || pageBids.isEmpty()){
                no_items_toShow=true;
                return null;
            }
            
            totalRows = bean.getResultNumber_of_bid(SessionBean.getUserName());
            
         
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
            mybids_pageItems.clear();
            int mybestbid=0;
            long seconds;
            Date date=new Date();
            for (Itemsbean it : pageItems) {
                Mybids_items in_item=new Mybids_items();
                in_item.setItem(it);
                
                //Set an image for this item
                byte[] image;
                image=ihi.getimage(it.getItemId());
                if(image!=null)
                {
                    in_item.setHas_image(true);
                    try{
                        FileOutputStream fos = new FileOutputStream("/Users/George/Desktop/TED/e-auction-2015/web/search_images/"+it.getItemId()+".jpg"); 
                        fos.write(image);
                        fos.close();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }else
                    in_item.setHas_image(false);
                
                //If auction completed check if user is winner and set the item as completed
                seconds = (it.getEnds().getTime() - date.getTime())/1000;
                if(seconds<=0){
                    in_item.setCompleted(true);
                    if(it.getWinner().equals(SessionBean.getUserName()))
                        in_item.setIwin(true);
                }
                
                //Set the maximum amount that user bidded for this item
                for (Bidsbean b : pageBids) {
                    if(b.getBid_itemid()==it.getItemId() && mybestbid<b.getAmount())
                        in_item.setBids(b);
                        mybestbid=b.getAmount();
                }
                
                mybids_pageItems.add(in_item);
            }
                return mybids_pageItems;
    }
    
    // Paging actions -----------------------------------------------------------------------------
    private void page(int firstRow) {
        this.firstRow = firstRow;
        searchItems();
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
 

}
