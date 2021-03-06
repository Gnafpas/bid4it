/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Beans.Bidsbean;
import Beans.Categoriesbean;
import Beans.Item_has_categorybean;
import Beans.Itemsbean;
import DAOs.UsersDAO;
import Beans.Usersbean;
import DAOs.BidsDAO;
import DAOs.CategoriesDAO;
import DAOs.Item_has_categoryDAO;
import DAOs.ItemsDAO;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.mail.*;
import javax.mail.internet.*;
import org.primefaces.context.RequestContext;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import helpers.Full_info_Item;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

@ManagedBean (name = "admin")
@SessionScoped
public class admin implements Serializable{
    Usersbean usr = new Usersbean();
    Usersbean usr2 = new Usersbean();
    private String msg ;
    private String subject ;
    private String xmlpath;

    public String getXmlpath() {
        return xmlpath;
    }

    public void setXmlpath(String xmlpath) {
        this.xmlpath = xmlpath;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    
    public Usersbean getUsr() {
        return usr;
    }
    
    public void setUsr(Usersbean user) {
        this.usr = user;
    }
    public Usersbean getUsr2() {
        return usr2;
    }
    
    public void setUsr2(Usersbean user) {
        this.usr2 = user;
    }
    public List<Usersbean > getAllActiveUsers() throws IOException {

        UsersDAO bean=new UsersDAO();
        List<Usersbean > allActiveUsers = bean.getAllActiveUsers();
  
        
        return allActiveUsers;
    }
   
    public List<Usersbean > getAllPendingUsers() throws IOException {

        UsersDAO bean=new UsersDAO();
        List<Usersbean > allPendingUsers = bean.getAllPendingUsers();
  
        
        return allPendingUsers;
    }
    
    public Usersbean  getUser(String key ) throws IOException {

        UsersDAO bean=new UsersDAO();
        Usersbean user = bean.getUser(key);
        if (user.isPending_state())
        {   usr=user;
            return usr;
        }
        else  
        {
            usr2 = user;
            return usr2;
        }
    }
    
    public int acceptUser(String key ) throws IOException 
    {   UsersDAO bean=new UsersDAO();
        if ((bean.acceptUser(key))==0)
        {   
            subject = "You have been accepted to bid4it";
            msg = "Hello "+key+" you have been accepted to bid4it; happy bidding!";
            sendEmail(usr.getEmail());
            
            return 0;
        }
        usr = null;
        return 1;  
    }
    
    public int deleteUser(String key ) throws IOException 
    {   UsersDAO bean=new UsersDAO();
         RequestContext context = RequestContext.getCurrentInstance();
         int temp = bean.deleteUser(key); 
        if (temp == 0)
        {
            usr=null;
            context.execute("PF('user_deleted').show();");
            return 0;
        }
        else if (temp == 2 )
        {    context.execute("PF('user_is_admin').show();");
        
        }
        return 1;  
    }
    
/**
 *
 * Sends email from admin to users for accept/reject registration or delete user.
 */
    public void sendEmail(String to)
    {

      final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
      String from = "bid4it.gr@gmail.com";
      String host = "smtp.gmail.com";
      Properties properties = System.getProperties();
      properties.setProperty("mail.smtp.host", host);
      properties.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
      properties.setProperty("mail.smtp.socketFactory.fallback", "false");
      properties.setProperty("mail.smtp.port", "587");
      properties.setProperty("mail.smtp.socketFactory.port", "587");
      properties.setProperty("mail.smtps.auth", "true");
      properties.setProperty("mail.smtp.starttls.enable", "true");
      Session session = Session.getDefaultInstance(properties, null);
       
      subject = "Welcome to bid4it!";
      try{

         MimeMessage message = new MimeMessage(session);
         message.setFrom(new InternetAddress(from));
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
         message.setSubject(subject);
         message.setText(msg);
         // Send message
         Transport transport = session.getTransport("smtp");
         transport.connect(host,from,"bid4itgr");
        transport.sendMessage(message, message.getAllRecipients());
         System.out.println("Sent message successfully...." + to);
      }catch (MessagingException mex) {
         mex.printStackTrace();
      }
    }
  
/**
 *
 * Export items data to xml form.
 */
    
    public void exportToXml() throws IOException  {
    ItemsDAO itemdao=new ItemsDAO();
    Item_has_categoryDAO ihcdao=new Item_has_categoryDAO();
    CategoriesDAO catdao=new CategoriesDAO();
    BidsDAO bidsdao=new BidsDAO();
    List <Itemsbean> items=new ArrayList();
   
    List <Full_info_Item> full_info_items=new ArrayList();
    List <Item_has_categorybean> ihc=new ArrayList();
    List <Bidsbean> bids=new ArrayList();
            
    //Create the xml and store it for downloading-----------------------------------------------------
    items=itemdao.getallitems();
    for(Itemsbean it :items){
        Full_info_Item  full_info_item=new Full_info_Item();
        full_info_item.setItem(it);
        ihc=ihcdao.getcategory_from_item(it.getItemId());
        List <Categoriesbean> cat=new ArrayList();
        for(Item_has_categorybean i:ihc){
            cat.add(catdao.getCategory(i.getCategoryId()));
        }
        full_info_item.setCategory(cat);
        bids=bidsdao.getitemsbids(it.getItemId());
        full_info_item.setBid(bids);
        
        full_info_items.add(full_info_item);
    }
    
    XStream xstream = new XStream(new DomDriver());
    try  {
      File file = new File("itemsexport.xml");
      FileWriter xmlFile ;
      xmlFile = new FileWriter(file);
      String xml = xstream.toXML(full_info_items);
      xmlFile.write("<?xml version=\"1.0\"?>\n" + xml);
      xmlFile.close();
    } catch (Exception ex)  {
      System.out.println(ex.getMessage());
    }
    
    
    
    //Send the xml to client-------------------------------------------------------------------------
     
    // Get the FacesContext
    FacesContext facesContext = FacesContext.getCurrentInstance();

    // Get HTTP response
    HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

    // Set response headers
    response.reset();   // Reset the response in the first place
    response.setHeader("Content-Type", "application/xml");  // Set only the content type

    // Open response output stream
    OutputStream responseOutputStream = response.getOutputStream();

    // Read PDF contents

    InputStream pdfInputStream = new FileInputStream("itemsexport.xml");

    // Read PDF contents and write them to the output
    byte[] bytesBuffer = new byte[2048];
    int bytesRead;
    while ((bytesRead = pdfInputStream.read(bytesBuffer)) > 0) {
        responseOutputStream.write(bytesBuffer, 0, bytesRead);
    }

    // Make sure that everything is out
    responseOutputStream.flush();

    // Close both streams
    pdfInputStream.close();
    responseOutputStream.close();

    // JSF doc:
    // Signal the JavaServer Faces implementation that the HTTP response for this request has already been generated
    // (such as an HTTP redirect), and that the request processing lifecycle should be terminated
    // as soon as the current phase is completed.
    facesContext.responseComplete();


   }
    
 
 
}