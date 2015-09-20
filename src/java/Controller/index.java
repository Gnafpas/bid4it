/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Beans.Bidsbean;
import Beans.Itemsbean;
import DAOs.BidsDAO;
import DAOs.Item_has_imageDAO;
import DAOs.ItemsDAO;
import helpers.Bidder_weight;
import helpers.Index_items;
import helpers.Item_weight;
import helpers.Recomended_item;
import java.io.FileOutputStream;
import java.io.Serializable;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
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
    private List <Recomended_item> recomendeditems = new ArrayList();
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

    public List<Recomended_item> getRecomendeditems() {
        return recomendeditems;
    }

    public void setRecomendeditems(List<Recomended_item> recomendeditems) {
        this.recomendeditems = recomendeditems;
    }
    
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
        if(firstRow==0)
          max_buy_price=-1;
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
            rowsPerPage = 10;
        pageRange = 10; // Default page range (max amount of page links to be displayed at once).
        if(searchSTR.trim().isEmpty())
            searchSTR="";
        if(searchCAT.equals("All Categories"))
            searchCAT="";
        allItems = bean.getitemsByCategory (searchCAT, searchSTR);
        if(allItems==null)
            return null;
        
        totalRows = bean.getResultNumber(searchCAT, searchSTR);
       
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
                        if (a.getBuy_price() > max_buy_price || a.getCurrently() > max_buy_price) {
                            iter.remove();
                            totalRows--;
                        }
                    }


            }

            pageItems = allItems.subList(firstRow, Math.min(allItems.size(),(firstRow + rowsPerPage)) );
           
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
        }else{   
            no_items_toShow=true;
            currentPage = 1;
            totalPages = 1;
            pages = new Integer[1];
            pages[0] = 1;
        }
        
           
        getRecomended_items();
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
 

    
    // Recomended items Algorythm / Function -----------------------------------------------------------------------------

    private void  getRecomended_items(){
    
      List <Integer> bids_itemids=new ArrayList();
      List <Bidder_weight> bidders_weight=new ArrayList();
      List <Item_weight> items_weight=new ArrayList();
      BidsDAO bidsdao=new BidsDAO();
      ItemsDAO itemdao=new ItemsDAO();
      String user=SessionBean.getUserName();
      
      recomendeditems.clear();
      
      if(user==null)
          return;
      
      //Get distinct items that had been bid form specific user
      bids_itemids=bidsdao.getmybids_distinct(user);
       
      for(int b: bids_itemids){

          //Get distinct users that had bid the item with the specific id
          List <String> bidders=new ArrayList();
          bidders=bidsdao.getitem_bidders_distinct(b);
          
          
          //Adds bidders to the bidders_weight list.If a bidder shows up for first time set weight to 1.Else increase weight by 1.
          if(bidders!=null){
              
              for(String bidder:bidders){
                  //We don't want to add to bidders_weight list the user that the recomended items intended to.
                  if(!bidder.equals(user)){
                       //Seek if bidder allready exist to bidders_weight list.If so just increase his weight.
                       boolean bidder_exists_to_bidders_weight=false;
                       for(Bidder_weight bw:bidders_weight){
                           if(bw.getBidder().equals(bidder)){
                               bw.setWeight(bw.getWeight()+1);
                               bidder_exists_to_bidders_weight=true;
                           }
                       }
                       //if he don't exist add him to list.
                       if(!bidder_exists_to_bidders_weight){
                            Bidder_weight bidder_weight=new Bidder_weight();
                            bidder_weight.setBidder(bidder);
                            bidder_weight.setWeight(1);
                            bidders_weight.add(bidder_weight);
                       }
                  }
              }
          }
      } 
      
      //Get items that have bidded in the past the neighbors and asign the weight from its bidder plus the sum_of_weights from previews items.
      //This is a way for choose a random item from the list of  recomended items in which each item has  a different factor of possibillity to be chosen.
      int sum_of_weights=0;
      for(Bidder_weight bw:bidders_weight){
          
            //Get all items that  specific user-neighbor has bidded at the past.
            List <Itemsbean> items=new ArrayList();
            List <Integer> bids=new ArrayList();
            bids=bidsdao.getmybids_distinct(bw.getBidder());
            
            for(Integer bi:bids){
                Itemsbean item=new Itemsbean();
                item=itemdao.getitem(bi);
                if(item!=null)
                   items.add(item);
            }
          
            //Add item id to recommended items list and asign the weight from its owner plus the sum_of_weights from previews items.
            for(Itemsbean it : items){

                //Check if the recommended item is active.If it is only then add it to recommended list
                if(it.getEnds()!=null){

                      Date date=new Date();
                      long seconds = (it.getEnds().getTime() - date.getTime())/1000;
                      if(seconds>0){

                            //If item had added to the list allready don't add it again.
                            boolean item_already_exists=false;
                            for(Item_weight it_weigt:items_weight){
                               if(it_weigt.getItem().getItemId()==it.getItemId())
                                   item_already_exists=true;
                            }
                            
                            if(!item_already_exists){
                                //Check if the user whose the recomended items is intended, has allready bid the specific item which is proposed to be recomend.If so dont add item to recomend item list
                                if(!bidsdao.check_bid_existance(it.getItemId(), user)){
                                      Item_weight item_weight=new Item_weight();
                                      item_weight.setItem(it);
                                      item_weight.setWeight(bw.getWeight()+sum_of_weights);
                                      items_weight.add(item_weight);
                                      sum_of_weights=sum_of_weights+bw.getWeight();
                                }
                            }

                      }

                }

            }
              
      }
          
      
      int num_of_recomended_items=0;
      int Max_num_of_recomended_items=3;

      //If the recomended items which found are less than the Maxnumber of recomended items tha will be represend at last to users page,or same,add them all.
      if(Max_num_of_recomended_items>=items_weight.size()){
          
          for(Item_weight  it_w:items_weight){
             
                Recomended_item recomendeditem=new Recomended_item();
                recomendeditem.setItem(it_w.getItem());

                //Add to recomended item image.If it hasn't add to it a default.
                recomendeditem.setImagepath("img/no_image.png");
                Item_has_imageDAO ihi=new Item_has_imageDAO();
                byte[] image=ihi.getimage(recomendeditem.getItem().getItemId());   
                if(image!=null){
                   try{
                       ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                       Properties properties = new Properties();
                       properties.load(classLoader.getResourceAsStream("config.properties"));
                       FileOutputStream fos = new FileOutputStream(properties.getProperty("application_path")+"/e-auction-2015/web/relateditems_images/"+recomendeditem.getItem().getItemId()+".jpg"); 
                       fos.write(image);
                       recomendeditem.setImagepath("relateditems_images/"+recomendeditem.getItem().getItemId()+".jpg");
                       fos.close();
                   }catch(Exception e){
                       e.printStackTrace();
                   }

                }
                
                recomendeditems.add(recomendeditem);
          }
      //If the recomended items which found are more than the Maxnumber of recomended items that will be represend at last to users page sellect random bear in mind the weight factor.
      }else{
      
                
                while(num_of_recomended_items<Max_num_of_recomended_items){

                    //Select a random recomended item with differnet factor of possibility each one.
                    Random rand = new Random();
                    int  random = rand.nextInt(sum_of_weights)+1;

                    int list_pointer=0;
                    int i=0;
                    for(Item_weight it_w:items_weight){
                           if(it_w.getWeight()<random){
                              list_pointer=i+1;
                           }
                           i++;
                    }

                    //Check if the random recomended item that chosen is allready on list.
                    boolean allready_at_r_list=false;
                    for(Recomended_item ritem:recomendeditems){
                        if(ritem.getItem().getItemId()==items_weight.get(list_pointer).getItem().getItemId()){
                            allready_at_r_list=true;
                        }
                    }

                    //If the random recomended item that chosen is not on list add it
                    if(!allready_at_r_list){


                        Recomended_item recomendeditem=new Recomended_item();
                        recomendeditem.setItem(items_weight.get(list_pointer).getItem());

                        //Add to recomended item image.If it hasn't add to it a default.
                        recomendeditem.setImagepath("img/no_image.png");
                        Item_has_imageDAO ihi=new Item_has_imageDAO();
                        byte[] image=ihi.getimage(recomendeditem.getItem().getItemId());   
                        if(image!=null){
                           try{
                               ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                               Properties properties = new Properties();
                               properties.load(classLoader.getResourceAsStream("config.properties"));
                               FileOutputStream fos = new FileOutputStream(properties.getProperty("application_path")+"/e-auction-2015/web/relateditems_images/"+recomendeditem.getItem().getItemId()+".jpg"); 
                               fos.write(image);
                               recomendeditem.setImagepath("relateditems_images/"+recomendeditem.getItem().getItemId()+".jpg");
                               fos.close();
                           }catch(Exception e){
                               e.printStackTrace();
                           }

                        }
 
                        recomendeditems.add(recomendeditem);
                        num_of_recomended_items++;
                    }
                }
                
      }
    
    }
}

