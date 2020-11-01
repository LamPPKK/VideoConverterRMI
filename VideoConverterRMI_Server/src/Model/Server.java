/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

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
            Registry registryFile = LocateRegistry.createRegistry(1099);
            registryFile.bind("FileInterface",fileImpl);
            System.out.println("Register FileImpl!");
            Registry registryConvert=LocateRegistry.createRegistry(1098);
            registryConvert.bind("ConvertInterface",convertImpl);
            System.out.println("Register ConvertImpl!");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
