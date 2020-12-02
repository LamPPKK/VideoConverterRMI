/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Flag.Flag;
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

    public String mergeFileInServer(String songName, int ep) throws FileNotFoundException, IOException {
        String folderPathServer = "C:\\Users\\DucVu\\Documents\\NetBeansProjects\\VideoConverterRMI\\VideoConverterRMI_Server\\Music";
        String serverPathMp4 = folderPathServer + "\\" + songName + ".mp4";
        FileOutputStream fos = new FileOutputStream(new File(serverPathMp4));
        long current = 0;
        for (int i = 1; i <= ep; i++) {
            String fileName = folderPathServer + "\\" + songName + "_EP" + i + ".mp4";
            File file = new File(fileName);
//            int size =(int) file.length();
//            byte[] data = new byte[size];
            FileInputStream fis = new FileInputStream(file);
            byte[] data=fis.readAllBytes();
//            size=fis.read(data, 0, size);
            current += data.length;
            fis.close();
            fos.write(data);
            System.out.println("Current " + current + " Kich thuoc them" + data.length);
        }
        fos.close();
        System.out.println("Merge done !");
        return serverPathMp4;
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
//            flag.setValue();
        } catch (Exception e) {
            System.out.println("Exception " + e);
            e.printStackTrace();
        }
    }

    @Override
    public byte[] DownloadFileFromServer(String serverPath,long current, int length) throws RemoteException {
        byte[] data=new byte[length];
        try{
            File file=new File(serverPath);
            FileInputStream fis=new FileInputStream(file);
            fis.skip(current);
            fis.read(data,0,length);
            fis.close();
        }
        catch(Exception e){
            System.out.println("Exception "+e);
            e.printStackTrace();
        } 
        return data;
    }
    
    public long getFileLength(String url){
        return new File(url).length();
    }

}
