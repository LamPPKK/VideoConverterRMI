/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Flag.Flag;
import Interface.FileInterface;
import java.rmi.RemoteException;

/**
 *
 * @author DucVu
 */
public class FileThreadUpload extends Thread{
    FileInterface fileInterface;
    Flag flag;
    String path;
    byte[] data;

    public FileThreadUpload(FileInterface fileInterface, String path, byte[] data, Flag flag) {
        this.fileInterface = fileInterface;
        this.path = path;
        this.data=data;
        this.flag=flag;
    }
    public void run(){
        try {
            fileInterface.UploadFileToServer(data, path);
            flag.setValue();
        } catch (RemoteException ex) {
            System.out.println("Exception "+ex);
            ex.printStackTrace();
        }
    }
}
