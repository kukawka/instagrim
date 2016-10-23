package uk.ac.dundee.computing.aec.instagrim.stores;

import java.util.Set;

/**
 *
 * @author Administrator
 */
public class UserDetails {
    private String name=null, surname=null, email=null, bio=null ;
    private boolean isEmailPublic=false, isSurnamePublic=false ;

    //String First_name=null ;
    public void UserDetails(){
        
    }
    
    public void setDetails(String[] details, boolean[] privacy){  
        name=details[1];
        surname=details[2] ;
        email=details[3] ;
        bio=details[4] ;
        isSurnamePublic=privacy[1] ;
        isEmailPublic=privacy[0] ;
    }
    
    public String getName(){
        return name ;
    }
    
    public String getSurname(){
        return surname ;
    }
    
    public String getEmail(){
        return email ;
    }
    
    public String getBio(){
        return bio ;
    }
    
    public boolean getIsSurnamePublic(){
        return isSurnamePublic ;
    }
    
    public boolean getIsEmailPublic(){
        return isEmailPublic ;
    }
}

