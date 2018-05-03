/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userapp;

import DataAccessLayer.DTO.Client;
import business.BusinessInterface;
import business.ClientBusinessClass;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Ali Alzantot
 */
public class LoginController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField email;
    @FXML
    private Button closeButton;
    @FXML
    private PasswordField password;
    private double xOffset;
    private double yOffset;
    BusinessInterface serverRef;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        /* Drag and Drop */
        borderPane.setOnMousePressed(event -> {
            xOffset = Main.getPrimaryStage().getX() - event.getScreenX();
            yOffset = Main.getPrimaryStage().getY() - event.getScreenY();
            borderPane.setCursor(Cursor.CLOSED_HAND);
        });

        borderPane.setOnMouseDragged(event -> {
            Main.getPrimaryStage().setX(event.getScreenX() + xOffset);
            Main.getPrimaryStage().setY(event.getScreenY() + yOffset);

        });

        borderPane.setOnMouseReleased(event -> {
            borderPane.setCursor(Cursor.DEFAULT);
        });
    }    

    @FXML
    private void loginButtonAction(ActionEvent event) {
        
        if(Platform.isFxApplicationThread()){
            try {
                Client c =serverRef.retreiveByEmailAndPass(email.getText(),password.getText());
                if(c!=null){
                    //JOptionPane.showMessageDialog(null,"Login Success ^_^ ");
                           
                    if(Platform.isFxApplicationThread()){
                            Stage stage;
                            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientMain.fxml"));
                                Parent root = loader.load();
                                ClientMainController controller = loader.getController();
                                controller.setsServerRef(serverRef);
                                controller.setClient(c);
                                ClientBusinessClass clientRef=new ClientBusinessClass();
                                clientRef.setC(controller);
                                serverRef.register(clientRef,c.getEmail().replaceAll("\\s+",""));
                                Scene scene = new Scene(root);
                                scene.getStylesheets().add(getClass().getResource("litechatui.css").toExternalForm());
                                stage.setTitle("Main View");
                                stage.setScene(scene);            
                                stage.show();
                            } catch (IOException ex) {
                                System.out.println("error in show register fxml from login fxml");
                                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                    }
                    else{
                        Platform.runLater(new Runnable(){

                            @Override
                            public void run() {
                               Stage stage;
                            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientMain.fxml"));
                                Parent root = loader.load();
                                ClientMainController controller = loader.getController();
                                controller.setsServerRef(serverRef);
                                controller.setClient(c);
                                ClientBusinessClass clientRef=new ClientBusinessClass();
                                clientRef.setC(controller);
                                serverRef.register(clientRef,c.getEmail().replaceAll(null, null));
                                Scene scene = new Scene(root);
                                stage.setTitle("Main View");
                                scene.getStylesheets().add(getClass().getResource("litechatui.css").toExternalForm());
                                stage.setScene(scene);            
                                stage.show();
                            } catch (IOException ex) {
                                System.out.println("error in show register fxml from login fxml");
                                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                            }                 
                                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }
                        });
                    } 
                    
                }
                else{
                    JOptionPane.showMessageDialog(null,"Error in Email and Password IF you don't have Account Please Register");
                }
            } catch (RemoteException ex) {
                System.out.println("error in getting clint inside Login controller");
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            Platform.runLater(new Runnable(){

                @Override
                public void run() {
                    try {
                        Client c =serverRef.retreiveByEmailAndPass(email.getText(),password.getText());
                        if(c!=null){
                            JOptionPane.showMessageDialog(null,"Login Success ^_^ ");
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Error in Email and Password IF you don't have Account Please Register");
                        }
                    } catch (RemoteException ex) {
                        System.out.println("error in getting clint inside Login controller");
                        Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            
            
            });
        }
        
        

    }
        
        

    

    @FXML
    private void minimizeWindow(ActionEvent event) {
        Main.getPrimaryStage().setIconified(true);        
    }

    @FXML
    private void closeSystem(ActionEvent event) {
        Platform.exit();
        System.exit(0);        
    }

    @FXML
    private void viewRegisterMethod(ActionEvent event) {
        
        if(Platform.isFxApplicationThread()){
                Stage stage;
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
                    Parent root = loader.load();
                    RegisterController controller = loader.getController();
                    controller.setsServerRef(serverRef);
                    Scene scene = new Scene(root, 350, 560);
                    stage.setTitle("Register");
                    stage.setScene(scene);            
                    stage.show();
                } catch (IOException ex) {
                    System.out.println("error in show register fxml from login fxml");
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
        
        }
        else{
            Platform.runLater(new Runnable(){

                @Override
                public void run() {
                   Stage stage;
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
                    Parent root = loader.load();
                    RegisterController controller = loader.getController();
                    controller.setsServerRef(serverRef);
                    Scene scene = new Scene(root, 350, 560);
                    stage.setTitle("Register");
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.initStyle(StageStyle.UNDECORATED);             
                    stage.show();
                } catch (IOException ex) {
                    System.out.println("error in show register fxml from login fxml");
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }                 
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
        } 
    }
    
    
    public void setsServerRef(BusinessInterface serverRef){
        this.serverRef=serverRef;
    }   
    
}
