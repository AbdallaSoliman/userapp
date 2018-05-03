/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userapp;

import DataAccessLayer.DTO.Client;
import business.BusinessInterface;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Mayada Saleh
 */
public class FXMLGroupChatInfoController implements Initializable {

    @FXML
     private Text FrName; 
    @FXML
     private Text FrPhone; 
    @FXML
     private Text FrStatues;
    @FXML
     private Text FrEmail; 
    @FXML
     private Text FrCity; 
    @FXML
     private Text FrBDate;
    @FXML
     private Text FrGender; 
    
    @FXML
    private ImageView FrImage;
    
    public Image img=new Image(getClass().getResourceAsStream("../images/away.png"));

    Client friend;
    BusinessInterface serverRef;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    
    }
    public void setsServerRef(BusinessInterface serverRef){
         this.serverRef=serverRef;
         
    }
    
    public void setClient(Client c){
        this.friend=c;
        
        FrName.setText(friend.getName());
        FrStatues.setText(friend.getStatus());
        FrCity.setText("City:          "+friend.getCity());
        FrPhone.setText("Phone:          "+friend.getPhone());
        FrEmail.setText("Email:          "+friend.getEmail());
        FrGender.setText("Gender:          "+friend.getGender());
        FrBDate.setText("BDate:          "+friend.getDate());
       // FrImage.setImage(ci.getImage()); 
        FrImage.setImage(img);
        FrImage.fitHeightProperty();
        FrImage.fitWidthProperty();
    } 
    
}