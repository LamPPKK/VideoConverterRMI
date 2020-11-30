/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Flag.Flag;
import Interface.FileInterface;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DucVu
 */
public class FileThreadDownload extends Thread{
    FileInterface fileInterface;
    String serverPath;
    String clientPath;
    long current;
    int length;
    byte[] data;

    public FileThreadDownload(FileInterface fileInterface, String serverPath, String clientPath, long current, int length) {
        this.fileInterface = fileInterface;
        this.serverPath = serverPath;
        this.clientPath = clientPath;
        this.current = current;
        this.length = length;
    }

    
    
    public void run(){
        try {
            this.data=fileInterface.DownloadFileFromServer(serverPath, current, length);
            File file=new File(clientPath);
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(file);
                fos.write(data);
                fos.close();
            } catch (Exception e){
                System.out.println("Exception "+e);
                e.printStackTrace();
            }
        } catch (RemoteException ex) {
            System.out.println("Exception "+ex);
            ex.printStackTrace();
        }
    }
   
}
