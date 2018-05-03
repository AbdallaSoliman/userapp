/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userapp;

import DataAccessLayer.DTO.Client;
import DataAccessLayer.DTO.Contact;
import DataAccessLayer.DTO.Groups;
import DataAccessLayer.DTO.Requests;
import business.BusinessInterface;
import com.sun.prism.paint.Color;
import java.io.IOException;
import static java.lang.String.format;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.YES_NO_OPTION;

/**
 * FXML Controller class
 *
 * @author Ali Alzantot
 */
public class ClientMainController implements Initializable {

    ArrayList<Client> cs = new ArrayList<Client>();
    ArrayList<Groups> gs = new ArrayList<Groups>();
    ArrayList<String> rs = new ArrayList<String>();
    ArrayList<Contact> cons = new ArrayList<Contact>();
    Client c;
    ClientMainController cmc;
    Client cChosen;
    Groups gChosen;
    int flagChosen = -1;

    BusinessInterface serverRef;

    @FXML
    private BorderPane borderPane;
    @FXML
    private Text friendName;
    @FXML
    private Text userName;
    @FXML
    private Text friendStatus;
    @FXML
    private ImageView friendImg;
    @FXML
    VBox requestList;
    @FXML
    ListView<Text> groupList;
    @FXML
    ListView<Text> friendList;
    @FXML
    TextField textField;
    private double xOffset;
    private double yOffset;
    @FXML
    private ImageView userImg;
    @FXML
    private MenuItem profile;
    @FXML
    private MenuItem logout;

    @FXML
    private ComboBox<?> userstatus;
    @FXML
    private HBox onlineUsersHbox;
    @FXML
    private Label onlineCount;
    @FXML
    private Button addFriend;
    @FXML
    private ImageView addfriend;
    @FXML
    private Label groupsCount;
    @FXML
    private Button addGroup;
    @FXML
    private ImageView creategroup;
    @FXML
    private HBox onlineUsersHbox1;
    @FXML
    private Label resquestsCount;
    @FXML
    private MenuBar friendMenu;
    @FXML
    private MenuItem unfriend;
    @FXML
    private MenuItem friendProfile;
    @FXML
    private MenuItem gInfo;
    @FXML
    private MenuItem leaveG;
    @FXML
    private ImageView menu;
    @FXML
    private ComboBox<?> font;
    @FXML
    private ComboBox<?> fontSize;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Menu infoMenu;
     public Image myImg=new Image(getClass().getResourceAsStream("l.png"));
     public Image friendimg=new Image(getClass().getResourceAsStream("n.jpg"));
     ArrayList<String> receiversEmails=new ArrayList<String>();
    ImageView old;
    ImageView myold;
    int flage=0;
    String lastTypedName="543";
    Groups choosenGroup;
    Client choosenClient;
    @FXML
    private VBox vb;
    
     
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         vb.setSpacing(5);
         cmc=this;
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
    private void minimizeWindow(ActionEvent event) {
        Main.getPrimaryStage().setIconified(true);
    }

    @FXML
    private void closeSystem(ActionEvent event) {
        //serverRef.unRegister(c);
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void enterAction(ActionEvent e) {
        System.out.println("ya 7aloly ya 7loly");
    }

    public void setFriendList(Client c) {
        friendList.getItems().clear();
        try {
            cs = serverRef.retreiveFriends(c);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Client x : cs) {
            String s = x.getName();
            s = x.getName().replaceAll("  ", "");
            friendList.getItems().add(new Text(s));
        }
    }

    public void setGroupList(Client c) {
        groupList.getItems().clear();
        try {
            gs = serverRef.retreiveGroups(c);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Groups x : gs) {
            String s = x.getName();
            s = x.getName().replaceAll("  ", "");
            groupList.getItems().add(new Text(s));
        }
    }

    public void setRequestList(Client C) {
        requestList.getChildren().clear();
        try {
            rs = serverRef.retreiveRequests(c);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (String x : rs) {
            final String s = x;
            Button accept = new Button("", new Text("accept"));
            Button decline = new Button("", new Text("Decline"));
            accept.setStyle("-fx-margin: 3");
            decline.setStyle("-fx-margin: 3");
            accept.setPrefSize(0, 0);
            decline.setPrefSize(0, 0);
            HBox reqBox = new HBox();
            x = x.replaceAll("  ", "");
            Text reqLabel = new Text(x);
            reqBox.getChildren().addAll(reqLabel, accept, decline);
            requestList.getChildren().add(reqBox);
            accept.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    acceptRequest(s);
                    setRequestList(c);
                    setFriendList(c);
                }
            });
            decline.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    DeclineRequest(s);
                    setRequestList(c);
                    setFriendList(c);
                }
            });
        }
    }

    private void acceptRequest(String sender) {
        int i = 0;
        try {
            i = serverRef.acceptRequest(sender, c.getEmail());
            System.out.println("i");
        } catch (RemoteException ex) {
            Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void DeclineRequest(String sender) {
        int i = 0;
        try {
            i = serverRef.declineRequest(sender, c.getEmail());
            System.out.println("i");
        } catch (RemoteException ex) {
            Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setClient(Client cc) {
        if(Platform.isFxApplicationThread()){
            this.c = cc;
            setRequestList(c);
            setFriendList(c);
            setGroupList(c);
            userName.setText(c.getName().replaceAll(" ", ""));
        }
        else{
            Platform.runLater(new Runnable(){

                @Override
                public void run() {
                    c = cc;
                    setRequestList(c);
                    setFriendList(c);
                    setGroupList(c);
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
        }
    }

    @FXML
    private void addFriendMethod(ActionEvent e) {
        int i = -100;
        String mailPane = JOptionPane.showInputDialog("Add Friend please write down the Email");
        try {
            i = serverRef.addFriend(c.getEmail(), mailPane);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(i);
    }

    @FXML
    private void unfriendMethod() {

        int unfriendPane;
        unfriendPane = JOptionPane.showConfirmDialog(null, "Would you like to unfriend " + friendName.getText(), "Unfriend", YES_NO_OPTION);
        if(unfriendPane==0){
            try {
                serverRef.unfriend(c.getEmail(), cChosen.getEmail());
                setFriendList(c);
                cChosen=null;
                friendName.setVisible(false);
                friendStatus.setVisible(false);
                friendImg.setVisible(false);
                infoMenu.setVisible(false);
                friendList.getSelectionModel().select(-1);
            } catch (RemoteException ex) {
                Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println(unfriendPane);

    }

      @FXML
    private void leaveGroupMethod(ActionEvent e) {

        int unfriendPane;
        unfriendPane = JOptionPane.showConfirmDialog(null, "Would you like to Leave " + friendName.getText(), "Unfriend", YES_NO_OPTION);
        if(unfriendPane==0){
            try {
                serverRef.leaveGroup(c.getEmail(),gChosen.getId());
                setGroupList(c);
                friendName.setVisible(false);
                friendStatus.setVisible(false);
                friendImg.setVisible(false);
                infoMenu.setVisible(false);
                gChosen=null;
                groupList.getSelectionModel().select(-1);
            } catch (RemoteException ex) {
                Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println(unfriendPane);

    }
    
    @FXML
    private void createGroupMethod() {
        int i = -100;
        System.out.println(i);
        String namePane = JOptionPane.showInputDialog("Please write down the Group's name");
        try {
            i = serverRef.createGroup(namePane, c);
            setGroupList(c);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(i);
    }
    
  
    @FXML
    private void groupInfoMethod(ActionEvent e) {

                    
        if(Platform.isFxApplicationThread()){
                Stage stage;
                stage = new Stage();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("GroupInfo.fxml"));
                    Parent root = loader.load();
                    GroupInfoController controller = loader.getController();
                    controller.setsServerRef(serverRef);
                    controller.setG(choosenGroup);
                    controller.setCmc(cmc);
                    controller.setStage(stage);
                    Scene scene = new Scene(root);
                    stage.setTitle("Group Information");
                    stage.setResizable(false);
                    stage.setScene(scene);            
                    stage.show();
                } catch (IOException ex) {
                    System.out.println("error in show friend Profile fxml from Main fxml");
                    Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
                }
        
        }
        else{
            Platform.runLater(new Runnable(){

                @Override
                public void run() {
             Stage stage;
                stage = new Stage();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("GroupInfo.fxml"));
                    Parent root = loader.load();
                    GroupInfoController controller = loader.getController();
                    controller.setsServerRef(serverRef);
                    controller.setG(choosenGroup);
                    controller.setCmc(cmc);
                    controller.setStage(stage);
                    Scene scene = new Scene(root);
                    stage.setTitle("Group Information");
                    stage.setResizable(false);
                    stage.setScene(scene);            
                    stage.show();
                } catch (IOException ex) {
                    System.out.println("error in show friend Profile fxml from Main fxml");
                    Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
                }
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
        } 
        
        
    }

    @FXML
    private void showFriend() {
        if (friendList.getSelectionModel().getSelectedItem() != null) {
            int it=0;
            System.out.println(friendList.getItems().indexOf(friendList.getSelectionModel().getSelectedItem()));
            it = friendList.getItems().indexOf(friendList.getSelectionModel().getSelectedItem());
            cChosen = cs.get(it);
            choosenClient=cChosen;
            if(cChosen.getName()!=null){
            friendName.setText(cChosen.getName().replace("  ", ""));
            friendName.setVisible(true);
            }
            if(cChosen.getStatus()!=null){
            friendStatus.setText(cChosen.getStatus().replace("  ", ""));
            friendStatus.setVisible(true);
            }
            friendImg.setVisible(true);
            infoMenu.setVisible(true);
            unfriend.setVisible(true);
            friendProfile.setVisible(true);
            gInfo.setVisible(false);
            leaveG.setVisible(false);
            receiversEmails.clear();
            receiversEmails.add(cChosen.getEmail().replaceAll("\\s+",""));
        }
        friendList.getSelectionModel().select(-1);
    }

    @FXML
    private void showGroup() {
         if (groupList.getSelectionModel().getSelectedItem() != null) {
            int it=1;
            System.out.println(groupList.getItems().indexOf(groupList.getSelectionModel().getSelectedItem()));
            it = groupList.getItems().indexOf(groupList.getSelectionModel().getSelectedItem());
            gChosen = gs.get(it);
            choosenGroup=gChosen;
             if(gChosen.getName()!=null){
            friendName.setText(gChosen.getName().replace("  ", ""));
            friendName.setVisible(true);
            }
            friendStatus.setText("");
            friendStatus.setVisible(true);
            friendImg.setVisible(true);
            friendProfile.setVisible(false);
            infoMenu.setVisible(true);
            unfriend.setVisible(false);
            gInfo.setVisible(true);
            leaveG.setVisible(true);
            receiversEmails.clear();
            ArrayList<Client> clients=new ArrayList<Client>();
             try {
                 clients=serverRef.reteriveClients(gChosen.getId().replaceAll("\\s+",""));
                 for (int i=0;i<clients.size();++i){
                     receiversEmails.add(clients.get(i).getEmail().replaceAll("\\s+",""));
                     System.out.println(clients.get(i).getEmail().replaceAll("\\s+",""));
                 }
             } catch (RemoteException ex) {
                 Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
         
        groupList.getSelectionModel().select(-1);
    }

    public void setsServerRef(BusinessInterface serverRef) {
        this.serverRef = serverRef;
    }

    
    @FXML
 public void viewProfile(){
            
        if(Platform.isFxApplicationThread()){
                Stage stage;
                stage = new Stage();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMyProfile.fxml"));
                    Parent root = loader.load();
                    FXMLMyProfileController controller = loader.getController();
                    controller.setsServerRef(serverRef);
                    controller.setClient(c);
                    Scene scene = new Scene(root);
                    stage.setTitle("My Profile");
                    stage.setResizable(false);
                    stage.setScene(scene);            
                    stage.show();
                } catch (IOException ex) {
                    System.out.println("error in show Profile fxml from Main fxml");
                    Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
                }
        
        }
        else{
            Platform.runLater(new Runnable(){

                @Override
                public void run() {
                Stage stage;
                stage = new Stage();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMyProfile.fxml"));
                    Parent root = loader.load();
                    FXMLMyProfileController controller = loader.getController();
                    controller.setsServerRef(serverRef);
                    controller.setClient(c);
                    Scene scene = new Scene(root);
                    stage.setTitle("My Profile");  
                    stage.setResizable(false);
                    stage.setScene(scene);            
                    stage.show();
                } catch (IOException ex) {
                    System.out.println("error in show Profile fxml from Main fxml");
                    Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
                }                 
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
        } 
    }
 
    @FXML
    public void onEnter (ActionEvent ae){
        if(receiversEmails.size()!=0){
            if(Platform.isFxApplicationThread()){
                try{
                        serverRef.tellOthers(textField.getText(),c.getName().replaceAll("\\s+",""),receiversEmails);
                        if(myold != null){
                            myold.setVisible(false);
                        }                
                        HBox temp=new HBox();
                        Text t=new Text(textField.getText());
                        t.getStyleClass().add("mymsg");
                        ImageView i=new ImageView();  
                        myold=i;
                        i.setImage(myImg);
                        i.setFitHeight(29);
                        i.setFitWidth(31);
                        i.getStyleClass().add("chatimg");
                        final Circle clip = new Circle(15, 15, 15);
                        i.setClip(clip);                    
                        temp.getChildren().add(t);                    
                        temp.getChildren().add(i);
                        temp.setAlignment(Pos.BASELINE_RIGHT);
                        if(flage == 0){
                            Text sname=new Text(c.getName().replace("  ", ""));
                            sname.getStyleClass().add("sname");  
                            HBox temp2=new HBox();
                            temp2.getChildren().add(sname);
                            temp2.setAlignment(Pos.BASELINE_RIGHT);
                            vb.getChildren().add(temp2);       
                            flage=1;
                        }
                        vb.getChildren().add(temp);                
                        textField.setText("");
               }
               catch(RemoteException ex){
                   ex.printStackTrace();
               } 
            }
            else{
                Platform.runLater(new Runnable(){

                    @Override
                    public void run() {
                        try{
                                serverRef.tellOthers(textField.getText(),c.getEmail().replaceAll("\\s+",""),receiversEmails);
                                if(myold != null){
                                    myold.setVisible(false);
                                }                
                                HBox temp=new HBox();
                                Text t=new Text(textField.getText());
                                t.getStyleClass().add("mymsg");
                                ImageView i=new ImageView();  
                                myold=i;
                                i.setImage(myImg);
                                i.setFitHeight(29);
                                i.setFitWidth(31);
                                i.getStyleClass().add("chatimg");
                                final Circle clip = new Circle(15, 15, 15);
                                i.setClip(clip);                    
                                temp.getChildren().add(t);                    
                                temp.getChildren().add(i);
                                temp.setAlignment(Pos.BASELINE_RIGHT);
                                if(flage == 0){
                                    Label sname=new Label(c.getName().replaceAll("\\s+",""));
                                    sname.getStyleClass().add("sname");  
                                    HBox temp2=new HBox();
                                    temp2.getChildren().add(sname);
                                    temp2.setAlignment(Pos.BASELINE_RIGHT);
                                    vb.getChildren().add(temp2);       
                                    flage=1;
                                }
                                vb.getChildren().add(temp);                
                                textField.setText("");
                       }
                       catch(RemoteException ex){
                           ex.printStackTrace();
                       } 
                    }

                });         


            } 
        }
        
 
    }
    
    
      public void print (String s,String name){
          
            if(Platform.isFxApplicationThread()){
                if(old != null){
                    old.setVisible(false);
                }                
                HBox temp=new HBox();
                Text t=new Text(s);
                t.getStyleClass().add("mymsg");
                ImageView ii=new ImageView();
                old=ii;
                ii.setFitHeight(29);
                ii.setFitWidth(31);
                ii.getStyleClass().add("chatimg");
                ii.setImage(friendimg);
                final Circle clip = new Circle(15, 15, 15);
                ii.setClip(clip);
                temp.getChildren().add(ii);
                temp.getChildren().add(t);
                if(!lastTypedName.equals(name)){
                    Text sname=new Text(name);
                    sname.getStyleClass().add("sname");
                    vb.getChildren().add(sname);
                    lastTypedName=name;
                }
                
                vb.getChildren().add(temp);
                flage=0;
            }
            else{
                Platform.runLater(new Runnable(){
                    
                    @Override
                    public void run() {
                        if(old != null){
                            old.setVisible(false);
                        }                
                        HBox temp=new HBox();
                        Text t=new Text(s);
                        t.getStyleClass().add("mymsg");
                        ImageView ii=new ImageView();
                        old=ii;
                        ii.setFitHeight(29);
                        ii.setFitWidth(31);
                        ii.getStyleClass().add("chatimg");
                        ii.setImage(friendimg);
                        final Circle clip = new Circle(15, 15, 15);
                        ii.setClip(clip);
                        temp.getChildren().add(ii);
                        temp.getChildren().add(t);
                        if(!lastTypedName.equals(name)){
                            Text sname=new Text(name);
                            sname.getStyleClass().add("sname");
                            vb.getChildren().add(sname);
                            lastTypedName=name;
                        }

                        vb.getChildren().add(temp);
                        flage=0;                  
                    }
                    
                });
            }
        
        
    }
      
      
      
    @FXML
 public void showFriendProfile(){
            
        if(Platform.isFxApplicationThread()){
                Stage stage;
                stage = new Stage();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFriendProfile.fxml"));
                    Parent root = loader.load();
                    FXMLGroupChatInfoController controller = loader.getController();
                    controller.setsServerRef(serverRef);
                    controller.setClient(choosenClient);
                    Scene scene = new Scene(root);
                    stage.setTitle(choosenClient.getName().replaceAll(" ", "")+" Profile");
                    stage.setResizable(false);
                    stage.setScene(scene);            
                    stage.show();
                } catch (IOException ex) {
                    System.out.println("error in show friend Profile fxml from Main fxml");
                    Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
                }
        
        }
        else{
            Platform.runLater(new Runnable(){

                @Override
                public void run() {
                 Stage stage;
                stage = new Stage();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFriendProfile.fxml"));
                    Parent root = loader.load();
                    FXMLGroupChatInfoController controller = loader.getController();
                    controller.setsServerRef(serverRef);
                    controller.setClient(choosenClient);
                    Scene scene = new Scene(root);
                    stage.setTitle(choosenClient.getName().replaceAll(" ", "")+" Profile");
                    stage.setResizable(false);
                    stage.setScene(scene);            
                    stage.show();
                } catch (IOException ex) {
                    System.out.println("error in show friend Profile fxml from Main fxml");
                    Logger.getLogger(ClientMainController.class.getName()).log(Level.SEVERE, null, ex);
                }
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
        } 
    }
 
public void refreshAfterRemoveGroup(){
    
                setGroupList(c);
                friendName.setVisible(false);
                friendStatus.setVisible(false);
                friendImg.setVisible(false);
                infoMenu.setVisible(false);
                gChosen=null;
                groupList.getSelectionModel().select(-1);

    
}
    
}
