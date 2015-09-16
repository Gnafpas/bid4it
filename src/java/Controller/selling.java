/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Beans.Itemsbean;
import DAOs.Item_has_imageDAO;
import DAOs.ItemsDAO;
import helpers.Seller_items;
import java.io.FileOutputStream;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

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
        for (Itemsbean it : pageItems_whithout_status) {
            getCoverImage (it.getItemId());
        }
        pageItems.clear();
        
        for (Itemsbean it : pageItems_whithout_status) {
           Seller_items items_with_status=new Seller_items();
           items_with_status.setItem(it);
           if(it.getEnds()==null)
               items_with_status.setActive(false);
           else
               items_with_status.setActive(true);
           pageItems.add(items_with_status);
        }
    }
    
    
    public void getCoverImage (int itemid)
    {   
        byte[] image;
        Item_has_imageDAO ihi=new Item_has_imageDAO();
        image=ihi.getimage(itemid);
        try{
            FileOutputStream fos = new FileOutputStream("/Users/George/Desktop/TED/e-auction-2015/web/search_images/"+itemid+".jpg"); 
            fos.write(image);
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
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
 
    
}
