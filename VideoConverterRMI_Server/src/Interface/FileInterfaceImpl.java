package Interface;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Interface.FileInterface;
import com.healthmarketscience.rmiio.GZIPRemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import com.healthmarketscience.rmiio.RemoteInputStreamServer;
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
    public void UploadFileToServer(RemoteInputStream ris,String serverPath,long length) throws RemoteException {
        try {
            int buffer=1024*64;
            int current=0;
            int size;
            InputStream is=RemoteInputStreamClient.wrap(ris);
            BufferedInputStream bis=new BufferedInputStream(is);
            BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(new File(serverPath)));
            while(current!=length){
                if(length-current>=buffer) size=buffer;
                else size=(int) length-current;
                byte[] data=new byte[size];
                bis.read(data,0,data.length);
                bos.write(data);
                bos.flush();
                current+=size;
            }
            bos.close();
            ris.close(true);
            System.out.println("Client has uploaded file to server !");
        } catch (Exception e){
            System.out.println("Exception "+e);
            e.printStackTrace();
        }
    }

    @Override
    public RemoteInputStream DownloadFileFromServer(String serverPath) throws RemoteException {
        System.out.println("Client start to download!");
        RemoteInputStreamServer riss = null;
        RemoteInputStream ris = null;
        try{
            riss=new GZIPRemoteInputStream(new BufferedInputStream(new FileInputStream(new File(serverPath))));
            ris=riss.export();
            riss=null;
        } catch (Exception e){
            System.out.println("Exception "+e);
            e.printStackTrace();
        } finally {
            if(riss!=null) riss.close();
        }
        return ris;
    }


}
