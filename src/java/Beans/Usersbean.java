
package Beans;



public class Usersbean {


    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String address_info;
    private String postcode;
    private String vatnumber;
    private boolean pending_state;
    private boolean adm;
    
    
    public Usersbean(String username, String password, String firstname, String lastname, String email, String phone,String address_info,String postcode,String vatnumber,boolean pending_state) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.address_info = address_info;
        this.postcode = postcode;
        this.vatnumber = vatnumber;
        this.pending_state = pending_state;
        
    }
    
    public Usersbean() {
        this.username = "";
        this.password = "";
        this.firstname = "";
        this.lastname = "";
        this.email = "";
        this.phone = "";
        this.address_info = "";
        this.postcode = "";
        this.vatnumber = "";
        this.pending_state = true;
        
    }    

    public boolean isAdm() {
        return adm;
    }

    public void setAdm(boolean adm) {
        this.adm = adm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress_info() {
        return address_info;
    }

    public void setAddress_info(String address_info) {
        this.address_info = address_info;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getVatnumber() {
        return vatnumber;
    }

    public void setVatnumber(String vatnumber) {
        this.vatnumber = vatnumber;
    }

    public boolean isPending_state() {
        return pending_state;
    }

    public void setPending_state(boolean pending_state) {
        this.pending_state = pending_state;
    }

    

    
}
