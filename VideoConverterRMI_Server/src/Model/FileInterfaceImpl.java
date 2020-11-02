/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.io.*;
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
    public byte[] DownloadFile(String filePath) throws RemoteException {
        File file = new File(filePath);
        int length = (int)file.length();
        byte[] data = new byte[length];
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            bis.read(data, 0, data.length);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Download done!");
        return data;
    }

    @Override
    public void SendFile(String filePath) throws RemoteException {
        File file = new File(filePath);
        int length = (int)file.length();
        byte[] data = new byte[length];
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(data, 0, data.length);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Sent!");
    }

    
    @Override
    public void SaveToFile(byte[] data, String filePath) throws RemoteException {
       String fileName=filePath + "\\" + "videoplayback.mp3";
        File file = new File(fileName);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();
        } catch (Exception e) {
            System.out.println("Client Exception : "+e);
        }
        System.out.println("Saved!");
    }
}
