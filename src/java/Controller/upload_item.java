/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Beans.Item_has_categorybean;
import Beans.Item_has_imagebean;
import Beans.Itemsbean;
import DAOs.CategoriesDAO;
import DAOs.Item_has_categoryDAO;
import DAOs.Item_has_imageDAO;
import DAOs.ItemsDAO;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author George
 */

@ManagedBean (name = "upload_item")
@SessionScoped
public class upload_item implements Serializable{
    
    private Itemsbean item=new Itemsbean();
    private Item_has_imagebean image1=new Item_has_imagebean();
    private Item_has_imagebean image2=new Item_has_imagebean();
    private Item_has_imagebean image3=new Item_has_imagebean();
    private Item_has_imagebean image4=new Item_has_imagebean();
    private Item_has_imagebean image5=new Item_has_imagebean();
    private List <String> categories_list=new ArrayList();
    private String currentcategory;
    private int categories_num=0;
    private UploadedFile file1;
    private UploadedFile file2;
    private UploadedFile file3;
    private UploadedFile file4;
    private UploadedFile file5;


    public Itemsbean getItem() {
        return item;
    }

    public void setItem(Itemsbean item) {
        this.item = item;
    }
          
    public int getCategories_num() {
        return categories_num;
    }

    public void setCategories_num(int categories_num) {
        this.categories_num = categories_num;
    }

    
    
    public String getCurrentcategory() {
        return currentcategory;
    }

    public void setCurrentcategory(String currentcategory) {
        this.currentcategory = currentcategory;
    }
    
    public UploadedFile getFile1() {
        return file1;
    }

    public void setFile1(UploadedFile file1) {
        this.file1 = file1;
    }

    public UploadedFile getFile2() {
        return file2;
    }

    public void setFile2(UploadedFile file2) {
        this.file2 = file2;
    }

    public UploadedFile getFile3() {
        return file3;
    }

    public void setFile3(UploadedFile file3) {
        this.file3 = file3;
    }

    public UploadedFile getFile4() {
        return file4;
    }

    public void setFile4(UploadedFile file4) {
        this.file4 = file4;
    }

    public UploadedFile getFile5() {
        return file5;
    }

    public void setFile5(UploadedFile file5) {
        this.file5 = file5;
    }
    
    public void add_to_category_list(){
        categories_list.add(currentcategory);
        categories_num++;
    }
    public void add_coordinates(){
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('location_added').show();");
    }
   

    public String storeItem_and_Imges() {
        RequestContext context = RequestContext.getCurrentInstance();
        if(item.getCountry().isEmpty() || item.getName().isEmpty() || item.getPostcode().isEmpty()){
            context.execute("PF('upload_item_empty_fields').show();");
            return "upload_item";
        }
        if(item.getFirst_bid()==0 && item.getBuy_price()==0 ){
            context.execute("PF('place_value_buyprice_firstbid').show();");
            return "upload_item";
        }
        
        int itemid;
        boolean upload_err=false;
        byte[] bFile1 = new byte[(int) file1.getSize()];
        byte[] bFile2 = new byte[(int) file2.getSize()];
        byte[] bFile3 = new byte[(int) file3.getSize()];
        byte[] bFile4 = new byte[(int) file4.getSize()];
        byte[] bFile5 = new byte[(int) file5.getSize()];
        
        try {
	     if(file1.getSize()>0){
               FileInputStream fileInputStream1 ;
               fileInputStream1=(FileInputStream) file1.getInputstream();
	       fileInputStream1.read(bFile1);
               fileInputStream1.close();
             }
             if(file2.getSize()>0){
               FileInputStream fileInputStream2 ;
               fileInputStream2=(FileInputStream) file2.getInputstream();
	       fileInputStream2.read(bFile2);
               fileInputStream2.close();
             }
             if(file3.getSize()>0){
               FileInputStream fileInputStream3 ;
               fileInputStream3=(FileInputStream) file3.getInputstream();
	       fileInputStream3.read(bFile3);
               fileInputStream3.close();
             }
             if(file4.getSize()>0){
               FileInputStream fileInputStream4 ;
               fileInputStream4=(FileInputStream) file4.getInputstream();
	       fileInputStream4.read(bFile4);
               fileInputStream4.close();
             }
             if(file5.getSize()>0){
               FileInputStream fileInputStream5 ;
               fileInputStream5=(FileInputStream) file5.getInputstream();
	       fileInputStream5.read(bFile5);
               fileInputStream5.close();
             }
	     
        } catch (Exception e) {
	     e.printStackTrace();
        }
        
        //Set seller and save item
        item.setSeller(SessionBean.getUserName());
        item.setCurrently(item.getFirst_bid());
        ItemsDAO saveitem=new ItemsDAO();
        itemid=saveitem.additem(item);
        //If item saved succesfully upload images and connect them with current item.Upload categories too and connect them with current item.
        if(itemid>=0){
          Item_has_imageDAO saveimage=new Item_has_imageDAO();
           
          if(bFile1.length!=0){
            image1.setImage(bFile1);
            image1.setItemeid(itemid);
            upload_err=saveimage.addimage(image1);
          }
          if(bFile2.length!=0){
            image2.setImage(bFile2);
            image2.setItemeid(itemid);
            upload_err=saveimage.addimage(image2);
          }
          if(bFile3.length!=0){
            image3.setImage(bFile3);
            image3.setItemeid(itemid);
            upload_err=saveimage.addimage(image3);
          }
          if(bFile4.length!=0){
            image4.setImage(bFile4);
            image4.setItemeid(itemid);
            upload_err=saveimage.addimage(image4);
          }
          if(bFile5.length!=0){
            image5.setImage(bFile5);
            image5.setItemeid(itemid);
            upload_err=saveimage.addimage(image5);
          }
          
          Item_has_categorybean items_category=new Item_has_categorybean();
          Item_has_categoryDAO  save_item_category=new Item_has_categoryDAO();
          CategoriesDAO category=new CategoriesDAO();
          int cat_id;
          for (String c : categories_list) {
             cat_id=category.getCategoryId(c);
             if(cat_id==0){
                 context.execute("PF('upload_item_err').show();"); 
                 return "index";
             }
             items_category.setCategoryId(cat_id);
             items_category.setItemId(itemid);
             upload_err=save_item_category.addcategory_to_item(items_category);
          }
          
          
          if(itemid>=0 && upload_err==false)
            context.execute("PF('upload_item').show();");
          else
            context.execute("PF('upload_item_err').show();"); 
        }
        return "index";
        
    }
    
    public void getitem(){
        ItemsDAO saveitem=new ItemsDAO();
        Itemsbean item2 = saveitem.getitem(4);
        Item_has_imageDAO saveimage=new Item_has_imageDAO();
        
        byte[] image = image2.getImage();
        
        try{
            FileOutputStream fos = new FileOutputStream("/Users/George/Desktop/test2.jpg"); 
            fos.write(image);
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
