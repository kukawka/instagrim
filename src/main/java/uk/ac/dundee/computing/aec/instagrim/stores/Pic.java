/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.stores;

import com.datastax.driver.core.utils.Bytes;
import java.nio.ByteBuffer;
import java.util.Set;
import java.util.Date;
//import java.sql.Timestamp;

/**
 *
 * @author Administrator
 */
public class Pic {

    private ByteBuffer bImage = null;
    private int length;
    private String type;
    private String description; 
    private java.util.UUID UUID=null;
    private Set<String>usersWhoLikedIt;
    private java.util.Date pic_added;
    
    public void Pic() {

    }
    public void setDesc(String description){
        this.description=description ;
    }
    

    public String getDesc(){
        return description ;
    }
    
    public void setLikes(Set<String> likes){
        usersWhoLikedIt=likes ;
    }
    
    public void setPicAdded(java.util.Date pic_added){
        this.pic_added=pic_added ;
    }
    
    public java.util.Date getPicAdded(){
        return pic_added ;
    }
    
    public Set<String> getLikes(){
        return usersWhoLikedIt ;
    }
    
    public void setUUID(java.util.UUID UUID){
        this.UUID =UUID;
    }
    
    public java.util.UUID getRealUUID(){
        return UUID ;
    }
    public String getSUUID(){
        return UUID.toString();
    }
    public void setPic(ByteBuffer bImage, int length,String type) {
        this.bImage = bImage;
        this.length = length;
        this.type=type;
    }

    public ByteBuffer getBuffer() {
        return bImage;
    }

    public int getLength() {
        return length;
    }
    
    public String getType(){
        return type;
    }

    public byte[] getBytes() {
         
        byte image[] = Bytes.getArray(bImage);
        return image;
    }

}
