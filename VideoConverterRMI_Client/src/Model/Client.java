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
import java.rmi.server.RMIClientSocketFactory;

/**
 *
 * @author Vu Minh Duc
 */
public class Client implements Runnable {

    MainView view;
    RMIClientSocketFactory scf;
    public ConvertInterface convertStub;
    public FileInterface fileStub;

//    private Socket clientSocket;
    public Client() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry();
        convertStub = (ConvertInterface) registry.lookup("ConvertInterface");
        fileStub = (FileInterface) registry.lookup("FileInterface");
        scf = new RMIClientSocketFactory() {
            @Override
            public Socket createSocket(String host, int port) throws IOException {
                return new Socket(host, port);
            }
        };
        view = new MainView();
        view.setClient(this);
    }

    @Override
    public void run() {
        view.setVisible(true);
    }

    public static void main(String[] args) throws RemoteException, NotBoundException {

    }
}
