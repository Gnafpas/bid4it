/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Beans.Itemsbean;
import DAOs.Item_has_imageDAO;
import DAOs.ItemsDAO;
import helpers.Index_items;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
 

/**
 *
 * @author adonis
 */
@ManagedBean (name = "index")
@SessionScoped
public class index implements Serializable{
    
    Itemsbean item = new Itemsbean();
    private List <Itemsbean> pageItems = new ArrayList();
    private List <Index_items> index_pageItems = new ArrayList();
    private List <Itemsbean> allItems = new ArrayList();
    private String criteria="";
    private int max_buy_price=-1;
    private String main_image="search_images/";
    private String searchSTR="";
    private String searchCAT="";
    private int totalRows;
    private int firstRow;
    private int rowsPerPage;
    private int totalPages;
    private int pageRange;
    private Integer[] pages;
    private int currentPage;
    private boolean no_items_toShow=false;

    public List<Itemsbean> getAllItems() {
        return allItems;
    }

    public void setAllItems(List<Itemsbean> allItems) {
        this.allItems = allItems;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public int getMax_buy_price() {
        return max_buy_price;
    }

    public void setMax_buy_price(int max_buy_price) {
        this.max_buy_price = max_buy_price;
    }
    

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

    public List<Index_items> getIndex_pageItems() {
        return index_pageItems;
    }

    public void setIndex_pageItems(List<Index_items> index_pageItems) {
        this.index_pageItems = index_pageItems;
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
    
    
    public void setSearchSTR(String searchSTR) {
        this.searchSTR = searchSTR;
    }

    public String getSearchSTR() {
        return searchSTR;
    }
    
        public void setSearchCAT(String searchCAT) {
        this.searchCAT = searchCAT;
    }

    public String getSearchCAT() {
        return searchCAT;
    }
    public String getMain_image() {
        return main_image;
    }

    public void setMain_image(String main_image) {
        this.main_image = main_image;
    }
    
    public List<Index_items > searchItems()
    {
        no_items_toShow=false;
        ItemsDAO bean=new ItemsDAO();
        if (rowsPerPage==0)
            rowsPerPage = 2;
        pageRange = 10; // Default page range (max amount of page links to be displayed at once).
        if(searchSTR.trim().isEmpty())
            searchSTR="";
        if(searchCAT.equals("All Categories"))
            searchCAT="";
        allItems = bean.getitemsByCategory (searchCAT, searchSTR);
        if(allItems==null)
            return null;
        if(!allItems.isEmpty()){
            if (criteria.equals("name") )
            {
                Collections.sort(allItems, new Comparator<Itemsbean>(){
                    @Override
                    public int compare(Itemsbean o1, Itemsbean o2){
                      return o1.name.compareToIgnoreCase(o2.name);
                    }
               });
            }
            else if (criteria.equals("currently") )
            {
                Collections.sort(allItems, new Comparator<Itemsbean>(){
                    public int compare(Itemsbean o1, Itemsbean o2){
                        if(o1.currently == o2.currently)
                            return 0;
                        return o1.currently < o2.currently ? -1 : 1;
                    }
               });

            }
            else if (criteria.equals("buy_price") )
            {
                Collections.sort(allItems, new Comparator<Itemsbean>(){
                    public int compare(Itemsbean o1, Itemsbean o2){

                        if(o1.buy_price == o2.buy_price)
                            return 0;
                        return o1.buy_price < o2.buy_price ? -1 : 1;
                    }
               });

            }
            if (max_buy_price!=-1)
            {
                for (Iterator<Itemsbean> iter = allItems.listIterator(); iter.hasNext(); ) {
                        Itemsbean a = iter.next();
                        if (a.getBuy_price() > max_buy_price) {
                            iter.remove();
                        }
                    }


            }
            //max_buy_price = -1;
            pageItems = allItems.subList(firstRow, Math.min(allItems.size(),(firstRow + rowsPerPage)) );
            totalRows = bean.getResultNumber(searchCAT, searchSTR);

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
            index_pageItems.clear();
            for (Itemsbean it : pageItems) {
                Index_items in_item=new Index_items();
                in_item.setItem(it);

                byte[] image;
                image=ihi.getimage(it.getItemId());
                if(image!=null)
                {
                    in_item.setHas_image(true);
                    try{
                        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                        Properties properties = new Properties();
                        properties.load(classLoader.getResourceAsStream("config.properties"));
                        FileOutputStream fos = new FileOutputStream(properties.getProperty("application_path")+"/e-auction-2015/web/search_images/"+it.getItemId()+".jpg"); 
                        fos.write(image);
                        fos.close();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }else
                    in_item.setHas_image(false);

                index_pageItems.add(in_item);
            }
        }else
           no_items_toShow=true;
        
        
                return index_pageItems;
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
