/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

/**
 *
 * @author George
 */
public class Item_has_imagebean {
    
    private int iditem_has_image;
    private int itemeid;
    private byte[] image;
    
    
    public Item_has_imagebean (int itemeid,byte[] image ) {
      
      this.itemeid=itemeid;
      this.image=image;
    }

    public Item_has_imagebean () {
      
    }
    
    public int getIditem_has_image() {
        return iditem_has_image;
    }

    public void setIditem_has_image(int iditem_has_image) {
        this.iditem_has_image = iditem_has_image;
    }

    public int getItemeid() {
        return itemeid;
    }

    public void setItemeid(int itemeid) {
        this.itemeid = itemeid;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
     
    
}
