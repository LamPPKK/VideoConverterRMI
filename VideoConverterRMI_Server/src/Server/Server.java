package Server;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Interface.FileInterfaceImpl;
import Interface.ConvertInterfaceImpl;
import com.healthmarketscience.rmiio.RemoteInputStreamServer;
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
    ServerSocket server;

    public Server() throws RemoteException, AlreadyBoundException, IOException {
        convertImpl = new ConvertInterfaceImpl();
        fileImpl = new FileInterfaceImpl();
        LocateRegistry.createRegistry(1099);
        Naming.rebind("rmi://localhost/file", fileImpl);
        System.err.println("FileImpl is available !");
        Naming.rebind("rmi://localhost/convert", convertImpl);
        System.err.println("ConvertImpl is available !");
    }

    public static void main(String[] args) throws IOException, RemoteException, AlreadyBoundException {
        new Server().run();
    }

    @Override
    public void run() {
        System.err.println("Server is ready !");
    }
}
