/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.rmi.*;
import java.io.*;
/**
 *
 * @author Vu Minh Duc
 */
public interface Convert extends Remote {
    public void ConvertFromFile(File source) throws RemoteException;
    public void ConvertFromYT() throws RemoteException;
}
