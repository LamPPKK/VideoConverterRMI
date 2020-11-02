/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

/**
 *
 * @author Vu Minh Duc
 */
public class Server {

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        try{
            ConvertInterfaceImpl convertImpl = new ConvertInterfaceImpl();
            FileInterfaceImpl fileImpl = new FileInterfaceImpl();
            Registry registry= LocateRegistry.createRegistry(1099);
            UnicastRemoteObject.exportObject(convertImpl, 1099);
            UnicastRemoteObject.exportObject(fileImpl, 1099);
            registry.bind("FileInterface",fileImpl);
            System.out.println("Register FileImpl!");
            registry.bind("ConvertInterface",convertImpl);
            System.out.println("Register ConvertImpl!");
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
