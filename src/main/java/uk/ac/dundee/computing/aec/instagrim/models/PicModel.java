package uk.ac.dundee.computing.aec.instagrim.models;

/*
 * Expects a cassandra columnfamily defined as
 * use keyspace2;
 CREATE TABLE Tweets (
 user varchar,
 interaction_time timeuuid,
 tweet varchar,
 PRIMARY KEY (user,interaction_time)
 ) WITH CLUSTERING ORDER BY (interaction_time DESC);
 * To manually generate a UUID use:
 * http://www.famkruithof.net/uuid/uuidgen
 */
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.Bytes;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.HashSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Set;
import javax.imageio.ImageIO;
import static org.imgscalr.Scalr.*;
import org.imgscalr.Scalr.Method;

import uk.ac.dundee.computing.aec.instagrim.lib.*;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;
//import uk.ac.dundee.computing.aec.stores.TweetStore;

public class PicModel {

    Cluster cluster;

    public void PicModel() {

    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public void insertPic(String filter, byte[] b, String type, String name, String user, String description2, boolean isprofile) {
        try {
            Convertors convertor = new Convertors();
            Set<String> likes=new HashSet<>() ;
            //likes.add("majed");
            
            String types[]=Convertors.SplitFiletype(type);
            ByteBuffer buffer = ByteBuffer.wrap(b);
            int length = b.length;
            java.util.UUID picid = convertor.getTimeUUID();
            
            //The following is a quick and dirty way of doing this, will fill the disk quickly !
            
            Boolean success = (new File("/var/tmp/instagrim/")).mkdirs();
            FileOutputStream output = new FileOutputStream(new File("/var/tmp/instagrim/" + picid));
            
             //output.write(b);
             
            //Writing the image as a single lump as shown above may use too much memory. 
            //Therefore I used a bufferedinput stream instead
            
            InputStream is = new ByteArrayInputStream(b);
            BufferedInputStream input = new BufferedInputStream(is);
            byte[] buffer1 = new byte[8192];
            for (int len = 0; (len = input.read(buffer1)) > 0;) {
                output.write(buffer1, 0, len);
            }
            output.close();
            
            byte []  thumbb = picresize(picid.toString(),types[1], filter);
            int thumblength= thumbb.length;
            ByteBuffer thumbbuf=ByteBuffer.wrap(thumbb);
            byte[] processedb = picdecolour(picid.toString(),types[1], filter);
            ByteBuffer processedbuf=ByteBuffer.wrap(processedb);
            int processedlength=processedb.length;
            
            Session session = cluster.connect("instagrim");
            String description = description2;
            PreparedStatement psInsertPic = session.prepare("insert into pics ( picid,image,thumb,processed, user, interaction_time,imagelength,thumblength,processedlength,type,name) values(?,?,?,?,?,?,?,?,?,?,?)");
            PreparedStatement psInsertPicToUser = session.prepare("insert into userpiclist ( picid, user,pic_added, isprofile, description, likes) values(?,?,?,?,?,?)");
            BoundStatement bsInsertPic = new BoundStatement(psInsertPic);
            BoundStatement bsInsertPicToUser = new BoundStatement(psInsertPicToUser);

            Date DateAdded = new Date();
            session.execute(bsInsertPic.bind(picid,buffer, thumbbuf,processedbuf, user, DateAdded, length,thumblength,processedlength, type, name));
            session.execute(bsInsertPicToUser.bind(picid, user, DateAdded, isprofile, description, likes));
            session.close();

        } catch (IOException ex) {
            System.out.println("Error --> " + ex);
        }
    }
    
    public Pic getLatestPicture(){
        Pic pic=new Pic() ;
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select pic_added, picid, description, likes from userpiclist");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind());
        if (rs.isExhausted()) {
            System.out.println("No Images returned");
            return null;
        } else {
            Calendar myCalendar = new GregorianCalendar(2014, 2, 11);
            Date myDate = myCalendar.getTime();

            for (Row row : rs) {   
                java.util.Date pic_added =row.getTimestamp("pic_added");
                java.util.UUID UUID = row.getUUID("picid");
                if(pic_added.compareTo(myDate)>0){
                String desc =row.getString("description");
                Set <String> likes=row.getSet("likes",String.class) ;
                System.out.println("UUID" + UUID.toString());
                pic.setLikes(likes);
                pic.setDesc(desc);
                pic.setUUID(UUID);
                pic.setPicAdded(pic_added);
                }

            }
        }
        return pic;
        
    }
    public void addALike(String user, String userwholiked, java.util.UUID picid) {
        java.util.Date pic_added=null ;
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select pic_added, picid from userpiclist where user =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        user));
        if (rs.isExhausted()) {
            System.out.println("Error");
        } else {
            for (Row row : rs) {
                java.util.UUID UUID = row.getUUID("picid");
                if(UUID.compareTo(picid)==0){
                pic_added =row.getTimestamp("pic_added");}
            }
        }
        session.close();
        Session session2 = cluster.connect("instagrim");
        Set <String> temp= new HashSet<>() ;
        temp.add(userwholiked);
            PreparedStatement psInsertPicToUser = session2.prepare("UPDATE userpiclist SET likes = likes + ? WHERE user=? and pic_added=? if  picid =?");
            BoundStatement bsInsertPicToUser = new BoundStatement(psInsertPicToUser);

            session2.execute(bsInsertPicToUser.bind(temp, user, pic_added, picid));
            session2.close();
    }
    
    public void deleteAPicture(String user,java.util.UUID picid) {
        java.util.Date pic_added=null ;
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select pic_added, picid from userpiclist where user =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        user));
        if (rs.isExhausted()) {
            System.out.println("Error");
        } else {
            for (Row row : rs) {
                java.util.UUID UUID = row.getUUID("picid");
                if(UUID.compareTo(picid)==0){
                pic_added =row.getTimestamp("pic_added");}
            }
        }
        session.close();
        Session session2 = cluster.connect("instagrim");
            PreparedStatement deletePicList = session2.prepare("DELETE FROM userpiclist WHERE user=? and pic_added=?");
            BoundStatement bsdeletePicList = new BoundStatement(deletePicList);
            
            PreparedStatement deletePic = session2.prepare("DELETE FROM Pics WHERE picid=?");
            BoundStatement bsdeletePic = new BoundStatement(deletePic);

            session2.execute(bsdeletePicList.bind(user, pic_added));
            session2.execute(bsdeletePic.bind(picid));
            
            session2.close();
    }


    public byte[] picresize(String picid,String type, String filter) {
        BufferedImage thumbnail=null ;
        try {
            BufferedImage BI = ImageIO.read(new File("/var/tmp/instagrim/" + picid));
            if(filter.equals("strengthened")){
                thumbnail = createThumbnailDRK(BI);
            }
            else if(filter.equals("sunny")){
                thumbnail = createThumbnailBR(BI);
            }
            else{
                thumbnail = createThumbnailBW(BI);
            }
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(thumbnail, type, baos);
            baos.flush();
            
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException et) {

        }
        return null;
    }

    public byte[] picdecolour(String picid,String type, String filter) {
       BufferedImage processed=null ;
        try {
            BufferedImage BI = ImageIO.read(new File("/var/tmp/instagrim/" + picid));
            if(filter.equals("strengthened")){
                processed = createProcessedDRK(BI);
            }
            else if(filter.equals("sunny")){
                processed = createProcessedBR(BI);
            }
            else{
                processed = createProcessedBW(BI);
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(processed, type, baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException et) {

        }
        return null;
    }

    public static BufferedImage createThumbnailBW(BufferedImage img) {
        img = resize(img, Method.SPEED, 250, OP_ANTIALIAS, OP_GRAYSCALE);
        // Let's add a little border before we return result.
        return pad(img, 2);
    }
    
    public static BufferedImage createThumbnailBR(BufferedImage img) {
        img = resize(img, Method.SPEED, 250, OP_BRIGHTER,OP_BRIGHTER,OP_ANTIALIAS);
        // Let's add a little border before we return result.
        return pad(img, 2);
    }
    
    public static BufferedImage createThumbnailDRK(BufferedImage img) {
        img = resize(img, Method.SPEED, 250, OP_DARKER, OP_ANTIALIAS );
        // Let's add a little border before we return result.
        return pad(img, 2);
    }
    
   public static BufferedImage createProcessedBW(BufferedImage img) {
        int Width=img.getWidth()-1;
        img = resize(img, Method.SPEED, Width, OP_ANTIALIAS, OP_GRAYSCALE);
        return pad(img, 4);
    }
   
      public static BufferedImage createProcessedBR(BufferedImage img) {
        int Width=img.getWidth()-1;
        img = resize(img, Method.SPEED, Width, OP_BRIGHTER,OP_BRIGHTER,OP_ANTIALIAS);
        return pad(img, 4);
    }
      
            public static BufferedImage createProcessedDRK(BufferedImage img) {
        int Width=img.getWidth()-1;
        img = resize(img, Method.SPEED, Width, OP_DARKER, OP_ANTIALIAS );
        return pad(img, 4);
    }
   
    public java.util.LinkedList<Pic> getPicsForUser(String User) {
        java.util.LinkedList<Pic> Pics = new java.util.LinkedList<>();
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select pic_added, picid, description, likes from userpiclist where user =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        User));
        if (rs.isExhausted()) {
            System.out.println("No Images returned");
            return null;
        } else {
            for (Row row : rs) {
                Pic pic = new Pic();
                java.util.Date pic_added =row.getTimestamp("pic_added");
                java.util.UUID UUID = row.getUUID("picid");
                String desc =row.getString("description");
                Set <String> likes=row.getSet("likes",String.class) ;
                System.out.println("UUID" + UUID.toString());
                pic.setLikes(likes);
                pic.setDesc(desc);
                pic.setUUID(UUID);
                pic.setPicAdded(pic_added);
                Pics.add(pic);

            }
        }
        return Pics;
    }
    /*
        public java.util.LinkedList<String> getDescriptionsForUser(String User) {
        java.util.LinkedList<String> descs = new java.util.LinkedList<>();
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select description from Pics where user =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        User));
        if (rs.isExhausted()) {
            System.out.println("No Images returned");
            return null;
        } else {
            for (Row row : rs) {
                String temp = row.getString("description");
                descs.add(temp);

            }
        }
        return descs;
    }*/
    
    public Pic getProfilePicForUser(String User) {
        Pic pic = null;

        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select * from userpiclist where user =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        User));
        if (rs.isExhausted()) {
            System.out.println("No Images returned");
            return null;
        } else {
            for (Row row:rs){
                //System.out.println("Success");
                if(row.getBool("isprofile")){
                pic=new Pic() ;
                java.util.UUID UUID = row.getUUID("picid");
                System.out.println("UUID" + UUID.toString());
                pic.setUUID(UUID);
                break ;
                }

            }
        }
        return pic;
    }

    public Pic getPic(int image_type, java.util.UUID picid) {
        Session session = cluster.connect("instagrim");
        ByteBuffer bImage = null;
        String type = null;
        int length = 0;
        try {
            Convertors convertor = new Convertors();
            ResultSet rs = null;
            PreparedStatement ps = null;
         
            if (image_type == Convertors.DISPLAY_IMAGE) {
                
                ps = session.prepare("select image,imagelength,type from pics where picid =?");
            } else if (image_type == Convertors.DISPLAY_THUMB) {
                ps = session.prepare("select thumb,imagelength,thumblength,type from pics where picid =?");
            } else if (image_type == Convertors.DISPLAY_PROCESSED) {
                ps = session.prepare("select processed,processedlength,type from pics where picid =?");
            }
            BoundStatement boundStatement = new BoundStatement(ps);
            rs = session.execute( // this is where the query is executed
                    boundStatement.bind( // here you are binding the 'boundStatement'
                            picid));

            if (rs.isExhausted()) {
                System.out.println("No Images returned");
                return null;
            } else {
                for (Row row : rs) {
                    if (image_type == Convertors.DISPLAY_IMAGE) {
                        bImage = row.getBytes("image");
                        length = row.getInt("imagelength");
                    } else if (image_type == Convertors.DISPLAY_THUMB) {
                        bImage = row.getBytes("thumb");
                        length = row.getInt("thumblength");
                
                    } else if (image_type == Convertors.DISPLAY_PROCESSED) {
                        bImage = row.getBytes("processed");
                        length = row.getInt("processedlength");
                    }
                    
                    type = row.getString("type");

                }
            }
        } catch (Exception et) {
            System.out.println("Can't get Pic" + et);
            return null;
        }
        session.close();
        Pic p = new Pic();
        p.setPic(bImage, length, type);

        return p;

    }

}
