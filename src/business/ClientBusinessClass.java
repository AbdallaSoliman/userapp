/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

/**
 *
 * @author Ali Alzantot
 */

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import userapp.ClientMainController;
public class ClientBusinessClass extends UnicastRemoteObject implements ClientBusinessInterface {
    ClientMainController c;

    public void setC(ClientMainController c) {
        this.c = c;
    }

    public ClientBusinessClass() throws RemoteException {
        
    }
    @Override
    public void receive(String msg, String name) throws RemoteException {
        
        c.print(msg, name);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
