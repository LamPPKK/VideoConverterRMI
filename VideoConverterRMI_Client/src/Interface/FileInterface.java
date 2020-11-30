/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Flag.Flag;
import java.rmi.*;
import java.io.*;

/**
 *
 * @author Vu Minh Duc
 */
public interface FileInterface extends Remote {

    public void UploadFileToServer(byte[] data, String serverPath) throws RemoteException;

    public byte[] DownloadFileFromServer(String serverPath, long current, int length) throws RemoteException;
    
    public String mergeFileInServer(String songName, int ep) throws RemoteException,FileNotFoundException,IOException;

}
