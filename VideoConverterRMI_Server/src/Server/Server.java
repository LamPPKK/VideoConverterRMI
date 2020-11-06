/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.net.*;
import java.io.*;

/**
 *
 * @author Vu Minh Duc
 */
public class Server implements Runnable {

    ConvertInterfaceImpl convertImpl;
    FileInterfaceImpl fileImpl;
    RMIServerSocketFactory ssf;
    ServerSocket server;

    public Server() throws RemoteException, AlreadyBoundException, IOException {
        convertImpl = new ConvertInterfaceImpl();
        fileImpl = new FileInterfaceImpl();
        ssf = new RMIServerSocketFactory() {
            @Override
            public ServerSocket createServerSocket(int port) throws IOException {
                return new ServerSocket(port);
            }
        };
        server=ssf.createServerSocket(3004);
//        Registry registry = LocateRegistry.createRegistry(1099);
//        UnicastRemoteObject.exportObject(fileImpl, 0, csf1, ssf1);
//        UnicastRemoteObject.exportObject(convertImpl, 0, csf1, ssf1);
        LocateRegistry.createRegistry(1099);
        Naming.bind("rmi://localhost/file", fileImpl);
        System.out.println("Register FileImpl!");
        Naming.bind("rmi://localhost/convert", convertImpl);
        System.out.println("Register ConvertImpl!");
    }

    public static void main(String[] args) throws IOException, RemoteException, AlreadyBoundException {
        new Server().run();
    }

    @Override
    public void run() {
        System.out.println("Server started!");
    }
}
