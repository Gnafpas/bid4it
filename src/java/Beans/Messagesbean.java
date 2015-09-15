/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.Serializable;

/**
 *
 * @author George
 */
public class Messagesbean implements Serializable {
    
    private int messageId;
    private String sender;
    private String receiver;
    private String message;
    private boolean checked;

    
    
    public Messagesbean(String sender, String receiver, String message, boolean checked) {
       
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.checked = checked;

        
    }
    
    public Messagesbean() {
        this.sender = "";
        this.receiver = "";
        this.message = "";
        this.checked = false;
        
    }   

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    
    
}
