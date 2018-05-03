/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userapp;

import DataAccessLayer.DTO.Client;
import DataAccessLayer.DTO.Contact;
import DataAccessLayer.DTO.Groups;
import DataAccessLayer.DTO.Has;
import business.BusinessInterface;
import java.awt.Dimension;
import static java.awt.SystemColor.window;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mayada Saleh
 */
public class GroupInfoController implements Initializable {
    
@FXML
 private Label gName;

@FXML 
private TextField addMembergp;

@FXML
private Label memberStatus;
@FXML
private Label memberName;
@FXML
private Button removemember;
@FXML
private HBox hboxMember;
@FXML
private VBox vbContainer;
@FXML
private Label gender;
@FXML
private Button deleteGroup;
@FXML
private ImageView addMemberImg;
@FXML
private Label gpID;
@FXML
private Label memberMail;


BusinessInterface serverRef;
    /**
     * Initializes the controller class.
     */

 ArrayList<Client> cs = new ArrayList<Client>();
 ArrayList<Groups> gs = new ArrayList<Groups>();
 ArrayList<Has> hm = new ArrayList<Has>();
 ClientMainController cmc;
  Client c;

    public void setCmc(ClientMainController cmc) {
        this.cmc = cmc;
    }


  Groups g;
    
  Stage stage;
  
  
    public void setStage(Stage stage) {
        this.stage = stage;
    }
  
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {


         }
    
   public void setsServerRef(BusinessInterface serverRef){
        this.serverRef=serverRef;
    } 
   
  
   
   
   // Delete group which empty of clientssss?!!!
  @FXML
   public void deleteGpMethod(){
       int i=0;
       Groups g = new Groups();
     //  String idd=gpID.getText().toString();
      // g.setId("2");
       
    try {
        g = serverRef.reteriveDeleteGroups(gpID.getText());  ///byms7 el group law mlhosh has bs :////
        System.out.println(g.getName());
        
        hm=serverRef.retreiveGroupRows(gpID.getText());
        int y;
        for (y=0;y<hm.size();y++)
        {
         serverRef.delete(hm.get(y));
        }
        i=serverRef.DeleteGroups(g);
         
        
        stage.close();
        cmc.refreshAfterRemoveGroup();
        
        System.out.println(i);
    } catch (RemoteException ex) {
        Logger.getLogger(GroupInfoController.class.getName()).log(Level.SEVERE, null, ex);
    }     
   }
   
   
   // Add member To group    lma bydeef byzhr fl run elly b3do msh f sa3tha 
  @FXML
public void onEnter(ActionEvent ae){
   System.out.println("test") ;
   Has h = new Has();
   h.setEmail(addMembergp.getText());   //Enter real mail in database 
   h.setId(gpID.getText());
    try {
        serverRef.create(h);
        vbContainer.getChildren().clear();
        
         try{
        ArrayList<Client> clients=serverRef.reteriveClients(gpID.getText());
        for (int i=0;i<clients.size();i++)
        { 
            hboxMember= new HBox();
            HBox hb=new HBox();
            hb.setSpacing(40);
            if(clients.get(i).getName() != null){
                memberName=new Label(clients.get(i).getName().replaceAll("\\s+","" ));
                memberName.setFont(new Font("Arial", 30));
            }
            if(clients.get(i).getStatus() != null){
                memberStatus=new Label(clients.get(i).getStatus().replaceAll("\\s+","" ));
                memberStatus.setFont(new Font("Arial", 30));
            }

            memberMail=new Label(clients.get(i).getEmail().replaceAll("\\s+","" ));
            memberMail.setFont(new Font("Arial", 30));
           
            if(memberStatus == null && memberName != null)
                hb.getChildren().addAll(memberName,new Label (""),memberMail);
           if(memberName == null && memberStatus != null)
               hb.getChildren().addAll(new Label (""),memberStatus,memberMail);
          if(memberName == null &&memberStatus == null )
               hb.getChildren().addAll(new Label (""),new Label (""),memberMail);
           if(memberName != null &&memberStatus != null )
               hb.getChildren().addAll(memberName,memberStatus,memberMail);
            vbContainer.getChildren().add(hb);
           
        }
        } catch (RemoteException ex) {
        Logger.getLogger(GroupInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    } catch (RemoteException ex) {
        Logger.getLogger(GroupInfoController.class.getName()).log(Level.SEVERE, null, ex);
    }    
}
 
@FXML  //de elly fyha moshkla msh btms7
public void removeMemberMethod (){
    Has obj=new Has();
   obj.setEmail(memberMail.getText());   
   obj.setId(gpID.getText());
    try { 
        
       serverRef.delete(obj);
    } catch (RemoteException ex) {
        Logger.getLogger(GroupInfoController.class.getName()).log(Level.SEVERE, null, ex);
    }

}



    public void setG(Groups g) {
        this.g = g;
 
        gName.setText(g.getName().replaceAll(" ", ""));
        gpID.setText(g.getId().replaceAll(" ", ""));
      // System.out.println("noooo");
        try{
        ArrayList<Client> clients=serverRef.reteriveClients(g.getId());
        for (int i=0;i<clients.size();i++)
        {
                      //  System.out.println(clients.size());
            final String s = clients.get(i).getEmail();
            
            HBox hb=new HBox();
            hb.setSpacing(40);
            if(clients.get(i).getName() != null){
                memberName=new Label(clients.get(i).getName().replaceAll("\\s+","" ));
                memberName.setFont(new Font("Arial", 30));
            }
            if(clients.get(i).getStatus() != null){
                memberStatus=new Label(clients.get(i).getStatus().replaceAll("\\s+","" ));
                memberStatus.setFont(new Font("Arial", 30));
            }
            memberMail=new Label(clients.get(i).getEmail().replaceAll("\\s+","" ));
            memberMail.setFont(new Font("Arial", 30));
            
           if(memberStatus == null && memberName != null)
                hb.getChildren().addAll(memberName,new Label (""),memberMail);
           if(memberName == null && memberStatus != null)
               hb.getChildren().addAll(new Label (""),memberStatus,memberMail);
          if(memberName == null &&memberStatus == null )
               hb.getChildren().addAll(new Label (""),new Label (""),memberMail);
           if(memberName != null &&memberStatus != null )
               hb.getChildren().addAll(memberName,memberStatus,memberMail);
            vbContainer.getChildren().add(hb);
            
           
        }
        } catch (RemoteException ex) {
        Logger.getLogger(GroupInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}