/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Interface.ConvertInterface;
import Interface.FileInterface;
import com.healthmarketscience.rmiio.GZIPRemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import java.io.*;
import java.rmi.RemoteException;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Vu Minh Duc
 */
public class MainController {

    public String getLink(JTextField in) {
        String link = in.getText();
        return link;
    }

    public File GetSelectedFile(JFileChooser choose) {
        File folder = choose.getSelectedFile();
        return folder;
    }

    public void ShowSelectedFile(JFileChooser choose, JTextField out) {
        out.setText(choose.getSelectedFile().getPath());
    }

    public void Convert1(RemoteInputStreamServer riss, FileInterface file, ConvertInterface convert, File source, File target) throws RemoteException, FileNotFoundException, IOException {
//        System.out.println("Ham convert1");

//        int bufferSize = 1024 * 64;
        String fileName = source.getName();
        String savePath = target + "\\" + fileName.substring(0, fileName.lastIndexOf(".")) + ".mp3";
        String serverPathMp4 = "C:\\Users\\DucVu\\Documents\\NetBeansProjects\\VideoConverterRMI_Real\\VideoConverterRMI_Server\\Music" + "\\" + fileName.substring(0, fileName.lastIndexOf(".")) + ".mp4";
        String serverPathMp3 = "C:\\Users\\DucVu\\Documents\\NetBeansProjects\\VideoConverterRMI_Real\\VideoConverterRMI_Server\\Music" + "\\" + fileName.substring(0, fileName.lastIndexOf(".")) + ".mp3";
        long length=source.length();
        //Upload file to server 
        try {
            file.UploadFileToServer(riss.export(), serverPathMp4, length);
            System.out.println("Upload done!");
        } catch (Exception e) {
            System.out.println("Exception " + e);
            e.printStackTrace();
        } finally {
            if (riss != null) {
                riss.close();
            }
        }

        // Convert 
        
        length = convert.ConvertFromFile(serverPathMp4, serverPathMp3);
        int current=0;     
        //Download File from server
        System.out.println("Downloading...");
        String clientPathMp4="C:\\Users\\DucVu\\Documents\\NetBeansProjects\\VideoConverterRMI_Real\\VideoConverterRMI_Client\\Music"+ "\\" + fileName.substring(0, fileName.lastIndexOf(".")) + ".mp4";
        int buffer=1024*64;
        int size;
        RemoteInputStream ris = file.DownloadFileFromServer(serverPathMp3);
        InputStream is = RemoteInputStreamClient.wrap(ris);
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(savePath)));
        while (current != length) {
            if (length - current >= buffer) {
                size = buffer;
            } else {
                size = (int) length - current;
            }
            byte[] data = new byte[size];
            bis.read(data, 0, data.length);
            bos.write(data);
            bos.flush();
            current += size;
        }
        bos.close();
        System.out.println("Download suscess ! File saved in "+savePath);
    }

    public void Convert2(FileInterface file, ConvertInterface convert, String link, File savePath) {

    }
}
