/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.dundee.computing.aec.instagrim.models;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;
import uk.ac.dundee.computing.aec.instagrim.lib.AeSimpleSHA1;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;

/**
 *
 * @author Administrator
 */
public class User {
    Cluster cluster;
    public User(){
        
    }
    
    public boolean RegisterUser(String username, String Password, String First_name, String Last_name, String Email, String Bio, boolean isEmailPublic, boolean isSurnamePublic){
        AeSimpleSHA1 sha1handler=  new AeSimpleSHA1();
        String EncodedPassword=null;
        try {
            EncodedPassword= sha1handler.SHA1(Password);
        }catch (UnsupportedEncodingException | NoSuchAlgorithmException et){
            System.out.println("Can't check your password");
            return false;
        }
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("insert into userprofiles (login,password,first_name,last_name,email, bio) Values(?,?,?,?,?,?)");
        PreparedStatement ps2 = session.prepare("insert into userprivacysettings (user, isEmailPublic, isSurnamePublic) Values(?,?,?)");
        //create a seperate table to store privacy settings 
        
        BoundStatement boundStatement = new BoundStatement(ps);
        session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username,EncodedPassword,First_name, Last_name, Email, Bio));
        //We are assuming this always works.  Also a transaction would be good here !
        
        BoundStatement boundStatement2 = new BoundStatement(ps2);
        session.execute( // this is where the query is executed
                boundStatement2.bind( // here you are binding the 'boundStatement'
                        username,isEmailPublic,isSurnamePublic));
        return true;
    }
    
    public boolean UpdateUserInfo(String username, String First_name, String Last_name, String Email, String Bio, boolean isEmailPublic, boolean isSurnamePublic){

        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("update userprofiles set first_name=?, last_name=?, email=?, bio=? where login=?");
        PreparedStatement ps2 = session.prepare("update userprivacysettings set isEmailPublic=?, isSurnamePublic=? where user=?");
        
        BoundStatement boundStatement = new BoundStatement(ps);
        session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        First_name, Last_name, Email, Bio, username));
        //We are assuming this always works.  Also a transaction would be good here !
        BoundStatement boundStatement2 = new BoundStatement(ps2);
        session.execute( // this is where the query is executed
                boundStatement2.bind( // here you are binding the 'boundStatement'
                       isEmailPublic, isSurnamePublic, username));
        return true;
    }
    
    public boolean doesUserExist(String username){
         Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select password from userprofiles where login =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username));
        if (rs.isExhausted()) {
            System.out.println("No users returned");
            return false;
        }
        else{
            return true;}
    }
    
    public boolean areAlreadyFriends(String user, String friend){
         Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select user from userprofiles where friend =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        friend));
        if (rs.isExhausted()) {
            System.out.println("Not friends");
            return false;
        }
        else{
            return true;}
    }
    
    public boolean IsValidUser(String username, String Password){
        AeSimpleSHA1 sha1handler=  new AeSimpleSHA1();
        String EncodedPassword=null;
        try {
            EncodedPassword= sha1handler.SHA1(Password);
        }catch (UnsupportedEncodingException | NoSuchAlgorithmException et){
            System.out.println("Can't check your password");
            return false;
        }
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select password from userprofiles where login =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username));
        if (rs.isExhausted()) {
            System.out.println("No Images returned");
            return false;
        } else {
            for (Row row : rs) {
               
                String StoredPass = row.getString("password");
                if (StoredPass.compareTo(EncodedPassword) == 0)
                    return true;
            }
        }
   
    
    return false;  
    }
    public String[] getUsersDetails(String user){
        String[] details=new String[5] ;
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select login, first_name, last_name, email, bio from userprofiles where login =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        user));
        if (rs.isExhausted()) {
            System.out.println("No data returned");
        } else {
            for (Row row : rs) {
               
                details[0]=  row.getString("login") ;
                details[1] = row.getString("first_name");
                details[2] = row.getString("last_name");
                details[3] = row.getString("email");
                details[4] = row.getString("bio");
                 
            }
        }
        return details ; 
    }
    
        public boolean[] getUsersSettings(String user){
        boolean[] settings=new boolean[2] ;
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select isEmailPublic,isSurnamePublic from userprivacysettings where user =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        user));
        if (rs.isExhausted()) {
            System.out.println("No data returned");
        } else {
            for (Row row : rs) {
               
                settings[0]=  row.getBool("isEmailPublic") ;
                settings[1] = row.getBool("isSurnamePublic");
                 
            }
        }
        return settings ; 
    }
        
        public Set<String> getLikedPictures(String user){
        Set <String> likedPictures= new HashSet<String>();
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select likedpictures where login =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        user));
        if (rs.isExhausted()) {
            System.out.println("No data returned");
        } else {
            for (Row row : rs) {
               
                //likedPictures=  row.get("likedpictures") ;
                
                 
            }
        }
        return likedPictures ; 
    }
    
    
    public boolean addFriend(String user, String friend){
        boolean friendadded=false ;
        if(areAlreadyFriends(user, friend)){
            return false ;
        }
        else{
            Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("insert into usersfriends (user, friend) Values(?,?)");
       
        BoundStatement boundStatement = new BoundStatement(ps);
        session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        user, friend));
        //We are assuming this always works.  Also a transaction would be good here !
        
        return true;
            
        }
        
    }
    
        
       public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }
    
}
