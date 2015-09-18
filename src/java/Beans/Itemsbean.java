
package Beans;


import java.io.Serializable;
import java.util.Date;

public class Itemsbean implements Serializable{
    

    private int itemId;
    private String name;
    private String seller;
    private String winner;
    private int currently;
    private int first_bid;
    private int number_of_bids;
    private String country;
    private String postcode;
    private String latitude;
    private String longtitude;
    private Date started;
    private Date ends;
    private String description;
    private int buy_price;
    
    
    
    public Itemsbean (String name,String seller,String winner,int currently,int first_bid,int number_of_bids,String country,String postcode,String latitude,String longtitude,Date started,Date ends,String description,int buy_price) {
      
      this.name=name;
      this.seller=seller;
      this.winner=winner;
      this.currently=currently;
      this.first_bid=first_bid;
      this.number_of_bids=number_of_bids;
      this.country=country;
      this.postcode=postcode;
      this.latitude=latitude;
      this.longtitude=longtitude;
      this.started=started;
      this.ends=ends;
      this.description=description;
      this.buy_price=buy_price;
    }
    
    
    public Itemsbean () {
      
    }


    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public int getCurrently() {
        return currently;
    }

    public void setCurrently(int currently) {
        this.currently = currently;
    }

    public int getFirst_bid() {
        return first_bid;
    }

    public void setFirst_bid(int first_bid) {
        this.first_bid = first_bid;
    }

    public int getNumber_of_bids() {
        return number_of_bids;
    }

    public void setNumber_of_bids(int number_of_bids) {
        this.number_of_bids = number_of_bids;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    public Date getEnds() {
        return ends;
    }

    public void setEnds(Date ends) {
        this.ends = ends;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBuy_price() {
        return buy_price;
    }

    public void setBuy_price(int buy_price) {
        this.buy_price = buy_price;
    }


    
    
}
