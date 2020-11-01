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
public interface FileInterface extends Remote{
    public byte[] DownloadFile(String fileName) throws RemoteException;
    public void SendFile(String fileName) throws RemoteException;
}
