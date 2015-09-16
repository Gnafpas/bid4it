/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Beans.Bidsbean;
import Beans.Categoriesbean;
import Beans.Item_has_categorybean;
import Beans.Item_has_imagebean;
import Beans.Itemsbean;
import Beans.Usersbean;
import DAOs.BidsDAO;
import DAOs.CategoriesDAO;
import DAOs.Item_has_categoryDAO;
import DAOs.Item_has_imageDAO;
import DAOs.ItemsDAO;
import DAOs.UsersDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import org.primefaces.context.RequestContext;
import helpers.Relateditems;
import static java.lang.System.out;
import java.util.Date;


/**
 *
 * @author George
 */
@ManagedBean (name = "item")
@SessionScoped


public class item implements Serializable{
    
    private int itemid_for_loading;
    private Itemsbean main_item;
    private Usersbean seller;
    private List<Categoriesbean>  item_categories=new ArrayList();
    private List <Relateditems> relateditems=new ArrayList();
    private List <Item_has_imagebean> item_has_images=new ArrayList();
    private String images_paths[]=new String[5];
    private String related_items_image_path[]=new String[4];
    private int main_image_pointer=0;
    private Categoriesbean  category;
    private boolean description_panel=true;
    private String loadmap_call;
    private String codeAddress_call;
    private long seconds; //time left for main item.
    private int bid;
    private String how_loacation_loaded_tomap="";

    public String getHow_loacation_loaded_tomap() {
        return how_loacation_loaded_tomap;
    }

    public void setHow_loacation_loaded_tomap(String how_loacation_loaded_tomap) {
        this.how_loacation_loaded_tomap = how_loacation_loaded_tomap;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }
    
    public String[] getRelated_items_image_path() {
        return related_items_image_path;
    }

    public void setRelated_items_image_path(String[] related_items_image_path) {
        this.related_items_image_path = related_items_image_path;
    }

    public int getMain_image_pointer() {
        return main_image_pointer;
    }

    public void setMain_image_pointer(int main_image_pointer) {
        this.main_image_pointer = main_image_pointer;
    }
    
    
    public int getItemid_for_loading() {
        return itemid_for_loading;
    }

    public void setItemid_for_loading(int itemid_for_loading) {
        this.itemid_for_loading = itemid_for_loading;
    }

    public String[] getImages_paths() {
        return images_paths;
    }

    public void setImages_paths(String[] images_paths) {
        this.images_paths = images_paths;
    }

    public List<Relateditems> getRelateditems() {
        return relateditems;
    }

    public void setRelateditems(List<Relateditems> relateditems) {
        this.relateditems = relateditems;
    }

    public List<Categoriesbean> getItem_categories() {
        return item_categories;
    }

    public void setItem_categories(List<Categoriesbean> item_categories) {
        this.item_categories = item_categories;
    }

    public Usersbean getSeller() {
        return seller;
    }

    public void setSeller(Usersbean seller) {
        this.seller = seller;
    }
    
    public Itemsbean getMain_item() {
        return main_item;
    }

    public void setMain_item(Itemsbean main_item) {
        this.main_item = main_item;
    }

    public boolean isDescription_panel() {
        return description_panel;
    }

    public void setDescription_panel(boolean description_panel) {
        this.description_panel = description_panel;
        load_map();
    }
    
   
    
    
    public void load_map(){
        String x;
        String y;
        String postcode;
        if(main_item==null)
            return;
        RequestContext requestContext = RequestContext.getCurrentInstance();   
        if(main_item.getLatitude()==null || main_item.getLongtitude()==null){
            x="0.0";y="0.0";
        }else{
            x=main_item.getLatitude();
            y=main_item.getLongtitude();
        }
        postcode=main_item.getPostcode();
        loadmap_call="loadMap("+x+","+y+")";
        requestContext.execute(loadmap_call);
        if(x.equals("0.0") && y.equals("0.0")){
            codeAddress_call="codeAddress('"+postcode+"')";
            requestContext.execute(codeAddress_call);
            how_loacation_loaded_tomap="Location loaded by item's postcode.";
        }else
            how_loacation_loaded_tomap="Location loaded by item's coordinates";
    }
    
    
    
    
    public void load_item(ComponentSystemEvent event){
        


        //Get item's info and categories
        seller=null;  
        List<Item_has_categorybean>  it_has_catlist=new ArrayList();
        List<Item_has_categorybean>  cat_has_itlist=new ArrayList();
        category=null;
        main_item=null;
        ItemsDAO i=new ItemsDAO();
        main_item=i.getitem(itemid_for_loading);
        if(main_item==null || main_item.getSeller()==null)
            return;
        UsersDAO u=new UsersDAO();
        seller=u.getUser(main_item.getSeller());
        if(seller==null)
            return;
       
        
        Item_has_categoryDAO ihc=new Item_has_categoryDAO();
        it_has_catlist=ihc.getcategory_from_item(main_item.getItemId());
        if(!it_has_catlist.isEmpty()){
            
           CategoriesDAO categorydao=new CategoriesDAO();
           item_categories.clear();
           for (Item_has_categorybean c : it_has_catlist) {
               category=categorydao.getCategory(c.getCategoryId());
               if(category!=null) 
                  item_categories.add(category);
           }
        }

        //Set time to left for the item to complete in seconds and call js function to display it in real-time
        Date date=new Date();
        seconds = (main_item.getEnds().getTime() - date.getTime())/1000;
        RequestContext requestContext = RequestContext.getCurrentInstance();  
        requestContext.execute("setsecs('"+seconds+"')");
        
        //Get rellated items
        relateditems.clear();
        if(!item_categories.isEmpty()){
           
           cat_has_itlist=ihc.getitems_from_categoryid(item_categories.get(0).getCategoryId());
           if(!cat_has_itlist.isEmpty()){

              Relateditems relateditem;
              int items_count=0;
              for (Item_has_categorybean it : cat_has_itlist) {
                  i=new ItemsDAO();
                  relateditem=new Relateditems();
                  relateditem.setItem(i.getitem(it.getItemId())); 
                  if(relateditem.getItem()!=null && items_count<4 && relateditem.getItem().getItemId()!=itemid_for_loading){ 
                     relateditems.add(relateditem);
                     items_count++;
                  }
              }
           }
        }
        
        
        //Display  item's images
        byte[] image;
        int j;
        for(j=0;j<5;j++){
            images_paths[j]= "img/no_image.png";
        }
        Item_has_imageDAO ihi=new Item_has_imageDAO();
        item_has_images=ihi.getimages(itemid_for_loading);
        if(item_has_images!=null){
          if(!item_has_images.isEmpty()){
            j=0;
            for (Item_has_imagebean it_h_im : item_has_images) {
                image=it_h_im.getImage();
                if(image!=null ){ 
                   try{
                      FileOutputStream fos = new FileOutputStream("/Users/George/Desktop/TED/e-auction-2015/web/items_images/"+it_h_im.getIditem_has_image()+".jpg"); 
                      fos.write(image);
                      if(j<5){
                        images_paths[j]="items_images/"+it_h_im.getIditem_has_image()+".jpg";
                      }
                      j++;
                      fos.close();
                   }catch(Exception e){
                      e.printStackTrace();
                   }
                }
           }
          }
        }
 
        //Display rellated items images
        j=0;
        if(relateditems==null){
            return;
        }
        if(relateditems.isEmpty()){
            return;
        }
        for (Relateditems rit : relateditems) {
            rit.setImagepath("img/no_image.png");
            image=ihi.getimage(rit.getItem().getItemId());   
            if(image!=null && j<4){
               try{
                   FileOutputStream fos = new FileOutputStream("/Users/George/Desktop/TED/e-auction-2015/web/relateditems_images/"+rit.getItem().getItemId()+".jpg"); 
                   fos.write(image);
                   rit.setImagepath("relateditems_images/"+rit.getItem().getItemId()+".jpg");
                   j++;
                   fos.close();
               }catch(Exception e){
                   e.printStackTrace();
               }
                
            }
        }
        
    }
    
    //Display another item's image at the large view.
    public void changemain_image(int newmain){
        main_image_pointer=newmain;
        //Set time to left for the item to complete in seconds and call js function to display it in real-time
        Date date=new Date();
        seconds = (main_item.getEnds().getTime() - date.getTime())/1000;
        RequestContext requestContext = RequestContext.getCurrentInstance();  
        requestContext.execute("setsecs('"+seconds+"')");
        load_map();
    }
    
    //Redirection to messages and write reiceiver text box with sellers name.Only if the user who request send message option is logged in.
    public String send_message_to_seller(){
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if(SessionBean.getUserName()==null){
            requestContext.execute("PF('log_in_first').show();");
            return "";
        }
        return "write_msg";
    }
        
    
    //When user's session is timed out delete all item images that he had requested.
    @PreDestroy
    public void destroy(){
      
        for(int j=0;j<5;j++){
          if(!images_paths[j].equals("img/no_image.png")){
            try{
    		File file = new File("/Users/George/Desktop/TED/e-auction-2015/web/"+images_paths[j]);
        	if(file.delete()){
    			System.out.println(file.getName() + " is deleted!");
    		}else{
    			System.out.println("Delete operation is failed.");
    		}
    	    }catch(Exception e){
    		e.printStackTrace();
    	    }
          }
        }
    }
    
    public void bidconfirmation(){
         RequestContext requestContext = RequestContext.getCurrentInstance();
         requestContext.execute("PF('bid_confirmation').show();");
         load_map();
    }
    
    public void bid(){
        RequestContext requestContext = RequestContext.getCurrentInstance();
       
        if(SessionBean.getUserName()==null){
            requestContext.execute("PF('log_in_first').show();");
            return;
        }
        if(SessionBean.getUserName().equals(main_item.getSeller())){
            requestContext.execute("PF('owner_buy_attempt').show();");
            return;
        }
        if(seconds<0){
            requestContext.execute("PF('item_not_for_sale').show();");
            return;
        }
        ItemsDAO itemsdao=new ItemsDAO();
        BidsDAO biddao=new BidsDAO();
        Bidsbean bidbean=new Bidsbean();
        bidbean.setAmount(bid);
        bidbean.setBid_itemid(itemid_for_loading);
        bidbean.setBidder(SessionBean.getUserName());
        Date date=new Date();
        bidbean.setTime(date);
        if(itemsdao.addbid(itemid_for_loading, bid, SessionBean.getUserName()) && !biddao.addbid(bidbean))
            requestContext.execute("PF('successful_bid').show();");
        else
            requestContext.execute("PF('unsuccessful_bid').show();");
    }
    
    public void buy_confirmation(){
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('buy_confirmation').show();");
        load_map();
    }
    
    public void buy(){
        RequestContext requestContext = RequestContext.getCurrentInstance();
        
        if(SessionBean.getUserName()==null){
            requestContext.execute("PF('log_in_first').show();");
            return;
        }
        if(SessionBean.getUserName().equals(main_item.getSeller())){
            requestContext.execute("PF('owner_buy_attempt').show();");
            return;
        }
        if(seconds<0){
            requestContext.execute("PF('item_not_for_sale').show();");
            return;
        }
        ItemsDAO itemsdao=new ItemsDAO();
        if(itemsdao.buyitem(itemid_for_loading, SessionBean.getUserName()))
            requestContext.execute("PF('successful_buy').show();");
        else
            requestContext.execute("PF('item_not_for_sale').show();");
       
    }
    
    public String redirection(){
        return "item";
    }
    
}
