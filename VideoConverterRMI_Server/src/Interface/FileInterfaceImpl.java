/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Interface.FileInterface;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vu Minh Duc
 */
public class FileInterfaceImpl extends UnicastRemoteObject implements FileInterface {

    public FileInterfaceImpl() throws RemoteException {
    }

    @Override
    public void UploadFileToServer(byte[] data, String serverPath) throws RemoteException {
        File serverFile = new File(serverPath);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(serverFile);
            fos.write(data);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            System.out.println("Exception " + e);
            e.printStackTrace();
        }
        System.out.println("Uploaded to server!");
    }

    @Override
    public byte[] DownloadFileFromServer(String serverPath) throws RemoteException {
        File serverFile = new File(serverPath);
        int length = (int) serverFile.length();
        byte[] data = new byte[length];
        FileInputStream fis;
        try {
            fis = new FileInputStream(serverFile);
            fis.read(data, 0, length);
        } catch (Exception e){
            System.out.println("Exception "+e);
            e.printStackTrace();
        }
        System.out.println("Downloaded from server!");
        return data;
    }

}
