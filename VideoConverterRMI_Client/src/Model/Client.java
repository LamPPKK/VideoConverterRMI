/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import View.MainView;
import java.io.*;
import java.util.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.net.*;

/**
 *
 * @author Vu Minh Duc
 */
public class Client {
    
    public ConvertInterface convertStub;
    public FileInterface fileStub;
    
//    private Socket clientSocket;

    public Client() throws RemoteException, NotBoundException {
//        clientSocket = new Socket("localhost", 3004);
//        clientSocket.setSoTimeout(20000);
        Registry registry=LocateRegistry.getRegistry();
        convertStub=(ConvertInterface) registry.lookup("ConvertInterface");
        fileStub=(FileInterface) registry.lookup("FileInterface");
    }

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Client client=new Client();
        MainView view=new MainView();
        view.setClient(client);  
        view.setVisible(true);
    }
    
}
