/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userapp;

import DataAccessLayer.DTO.Client;
import business.BusinessInterface;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Mayada Saleh
 */
public class FXMLMyProfileController implements Initializable {

    @FXML
    private TextField MyName;
    @FXML
    private TextField MyPhone;
    @FXML
    private TextArea MyStatus;
    @FXML
    private Label MyEmail;
    @FXML
    private TextField MyCity;
    @FXML
    private TextField MyBDate;
    @FXML 
    private TextField MyPassword;
    @FXML
    private ImageView MyImage;
    @FXML
    private RadioButton maleGender;
    @FXML
    private RadioButton femaleGender;
    Client ci;
    BusinessInterface serverRef;
   @FXML
   private ToggleGroup genderGroup = new ToggleGroup();
   @FXML 
   private Button saveButton;
   


    //public Image img=new Image(getClass().getResourceAsStream("boo.jpg"));
   public Image img;
   Client c;



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        maleGender.setToggleGroup(genderGroup);
        maleGender.setUserData("male");
        
        femaleGender.setToggleGroup(genderGroup);
        femaleGender.setUserData("female");
    }
      
         public void setsServerRef(BusinessInterface serverRef){
             this.serverRef=serverRef;
       /* try {
            System.out.println("eror");
            Client h=new Client();
            h=serverRef.retreiveClient("abdalla@saam.com");
            MyCity.setText(h.getCity());
            MyName.setText(h.getName());
            MyPhone.setText(h.getPhone());
            MyStatus.setText(h.getStatus());
            MyEmail.setText(h.getEmail());
            MyPassword.setText(h.getPassword());
            MyBDate.setText(h.getDate().toString());
             } catch (RemoteException ex) {
            Logger.getLogger(FXMLMyProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
             
    }
    
    
    @FXML
    private void uploadPhotoMethod() {
        if(Platform.isFxApplicationThread()){
            FileChooser fileChooser = new FileChooser();
            //Set extension filter
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

            File selectedFile = fileChooser.showSaveDialog(null);          

            if(selectedFile !=null){
                try {
                    BufferedImage bufferedImage = ImageIO.read(selectedFile);
                    img = SwingFXUtils.toFXImage(bufferedImage, null);
                    MyImage.setImage(img);

                } catch (IOException ex) {
                    System.out.println("error in uploading photo");
                    //Lo1gger.getLogger(FXMLMyProfileController.class.getName()).log(Level.SEVERE, null, ex);
                }  
            }
        
        }
        else{
            
            Platform.runLater(new Runnable() {

                @Override
                public void run() {
                    
                    FileChooser fileChooser = new FileChooser();
                    //Set extension filter
                    FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
                    FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
                    fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

                    File selectedFile = fileChooser.showSaveDialog(null);          
                    if(selectedFile !=null){
                        try {
                            BufferedImage bufferedImage = ImageIO.read(selectedFile);
                            img = SwingFXUtils.toFXImage(bufferedImage, null);
                            MyImage.setImage(img);

                        } catch (IOException ex) {
                            System.out.println("error in uploading photo");
                            //Lo1gger.getLogger(FXMLMyProfileController.class.getName()).log(Level.SEVERE, null, ex);
                        }  
                    }
                    
                   // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            }); 
        }      
    }
    
    
//    public void setsServerRef(BusinessInterface serverRef){
//        this.serverRef=serverRef;
//    }   
//    
    public void setClient(Client c){
        System.out.println("ali from method");
        this.ci=c;

        MyCity.setText(ci.getCity());
        MyName.setText(ci.getName());
        MyPhone.setText(ci.getPhone());
        MyStatus.setText(ci.getStatus());
        MyEmail.setText(ci.getEmail());
        MyPassword.setText(ci.getPassword().replaceAll(" ",""));
        if(ci.getDate()!=null)
        MyBDate.setText(ci.getDate().toString());
        System.out.println("ali ");
        //MyImage.setImage(img);
        //MyImage.fitHeightProperty();
        //MyImage.fitWidthProperty();
        if(ci.getGender()!=null){
            if(ci.getGender().replaceAll(" ", "").equalsIgnoreCase("Female")){
               femaleGender.setSelected(true);
            } else {
               maleGender.setSelected(true);
             }
        }
           }
    
    @FXML
    public void updateMyProfile(){
      try { 
          
           // Client ct = new Client();
         Client ct=serverRef.retreiveClient("abdalla@saam.com");
            /*        
            ct.setEmail((MyEmail.getText()));
            ct.setName(MyName.getText());
            ct.setCity(MyCity.getText());
            ct.setPassword(MyPassword.getText());
            ct.setPhone(MyPhone.getText());
            ct.setStatus(MyStatus.getText());
            ct.setDate(java.sql.Date.valueOf("3017-09-09"));
            ct.setImage(MyImage.getImage());
            System.out.println("updateeeee");
            ct.setGender(genderGroup.getSelectedToggle().getUserData().toString());
            
            System.out.println("b3de");
*/
          System.out.println(ct.getGender());
          System.out.println(ct.getCity());
  //         int x= serverRef.update(ct);
           //System.out.println(x);
        } catch (RemoteException ex) {
            Logger.getLogger(FXMLMyProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    

}
