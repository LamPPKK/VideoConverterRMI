/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Flag.Flag;
import Interface.ConvertInterface;
import Interface.FileInterface;
import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public void startConvertFromFile(FileInterface file, ConvertInterface convert, File source, File target) throws RemoteException, FileNotFoundException, IOException {

        long length = source.length();
        long current = 0;
        int buffer = 1024 * 64;
        int ep = 0;
        ArrayList<Flag> arrF = new ArrayList<Flag>();
        String fileName = source.getName();
        String folderPathServer = "C:\\Users\\DucVu\\Documents\\NetBeansProjects\\VideoConverterRMI\\VideoConverterRMI_Server\\Music";
        String songName = fileName.substring(0, fileName.lastIndexOf("."));
        String serverPathMp3 = folderPathServer + "\\" + songName + ".mp3";
        byte[] dataUpload;
        //Upload file to server
        FileInputStream fis = new FileInputStream(source);
        while (current != length) {
            if (length - current > buffer) {
                ep++;
                Flag tmp = new Flag();
                arrF.add(tmp);
                dataUpload = new byte[buffer];
                int size = fis.read(dataUpload, 0, buffer);
                current += buffer;
                String path = folderPathServer + "\\" + songName + "_EP" + ep + ".mp4";
                FileThreadUpload fThread = new FileThreadUpload(file, path, dataUpload, tmp);
                fThread.start();
                System.out.println("Sending " + (float) (current * 100 / length) + "% !" + "Current : " + current + "  Size " + size);
            } else {
                ep++;
                Flag tmp = new Flag();
                arrF.add(tmp);
                int size = (int) (length - current);
                dataUpload = new byte[size];
                fis.read(dataUpload, 0, size);
                fis.close();
                current = length;
                String path = folderPathServer + "\\" + songName + "_EP" + ep + ".mp4";
                FileThreadUpload fThread = new FileThreadUpload(file, path, dataUpload, tmp);
                fThread.start();
                System.out.println("Sending 100% ! " + "Current " + current);
            }
        }
        
        boolean dk = true;
        while(dk){
            int dem=0;
            for(int i=0;i<arrF.size();i++){
                if(arrF.get(i).getValue()==true) dem++;
            }
            if(dem==ep) dk=false;
        }
        
        arrF.clear();
        
        //Merge file
        System.out.println("Start merge");
        String serverPathMp4 = file.mergeFileInServer(songName, ep);
        System.out.println("Merge done!");
        
        
        //Convert
        System.out.println("Start convert!");
        length = convert.ConvertFromFile(serverPathMp4, serverPathMp3);
        System.out.println("Convert done!");
        //Download File from server

        System.out.println("Start download!");
        current = 0;
        ep = 0;
        System.out.println("Length : " + length);
        System.out.println("Current : " + current);
        String folderPathClient = "C:\\Users\\DucVu\\Documents\\NetBeansProjects\\VideoConverterRMI\\VideoConverterRMI_Client\\Music";
        while (current != length) {
            if (length - current > buffer) {
                ep++;
                Flag tmp = new Flag();
                arrF.add(tmp);
                current += buffer;
                String path = folderPathClient + "\\" + songName + "_EP" + ep + ".mp3";
                FileThreadDownload fThread = new FileThreadDownload(file, serverPathMp3, path, current, buffer, tmp);
                fThread.start();
                System.out.println("Download " + (float) (current * 100 / length) + "% !" + "Current : " + current + "  Size " + buffer);
            } else {
                ep++;
                Flag tmp = new Flag();
                arrF.add(tmp);
                int size = (int) (length - current);
                current = length;
                String path = folderPathClient + "\\" + songName + "_EP" + ep + ".mp3";
                FileThreadDownload fThread = new FileThreadDownload(file, serverPathMp3, path, current, size, tmp);
                fThread.start();
                System.out.println("Download 100% ! " + "Current " + current);
            }
        }
        
        
        dk = true;
        while(dk){
            int dem=0;
            for(int i=0;i<arrF.size();i++){
                if(arrF.get(i).getValue()==true) dem++;
            }
            if(dem==ep) dk=false;
        }
        
        //Save
        System.out.println("Saving...");
        String savePath = target + "\\" + songName + ".mp3";
        File save = new File(savePath);
        FileOutputStream fos = new FileOutputStream(save);
        for (int i = 1; i <= ep; i++) {
            String filePath = folderPathClient + "\\" + songName + "_EP" + i + ".mp3";
            File filePart = new File(filePath);
            int size = (int) filePart.length();
            byte[] dataSave = new byte[size];
            fis = new FileInputStream(filePart);
            fis.read(dataSave, 0, size);
            fis.close();
            fos.write(dataSave);
        }
        fos.close();
        System.out.println("Saved in " + save.getPath().toString());

    }

    public void startConvertFromLinkYT(FileInterface file, ConvertInterface convert, String url, String folder) throws RemoteException, FileNotFoundException, IOException {
        ArrayList<Flag> arrF = new ArrayList<Flag>();
        
        long current = 0, length = 0;
        int buffer = 1024 * 64;
        System.out.println("SaveFolder : " + folder);

        String serverPath = convert.ConvertFromYT(url);
        System.out.println("Server Path : "+serverPath);
//        String serverPath = "C:\\Users\\DucVu\\Documents\\NetBeansProjects\\VideoConverterRMI\\VideoConverterRMI_Server\\youtube_dl\\[LIVE] 2016 Kim Hee Seon and Jackie Chan - Endless Love live.mp4";
        length = file.getFileLength(serverPath);
        System.out.println("Length : " + length);
        String songName = serverPath.substring(serverPath.lastIndexOf("\\"), serverPath.lastIndexOf("."));
        String folderClient = "C:\\Users\\DucVu\\Documents\\NetBeansProjects\\VideoConverterRMI\\VideoConverterRMI_Client\\Music";

        System.out.println("Start download!");
        int ep = 0;
        while (current != length) {
            if (length - current > buffer) {
                ep++;
                Flag tmp = new Flag();
                arrF.add(tmp);
                current += buffer;
                String path = folderClient + "\\" + songName + "_EP" + ep + ".mp3";
                FileThreadDownload fThread = new FileThreadDownload(file, serverPath, path, current, buffer, tmp);
                fThread.start();
                System.out.println("Download " + (float) (current * 100 / length) + "% !" + "Current : " + current + "  Size " + buffer);
            } else {
                ep++;
                Flag tmp = new Flag();
                arrF.add(tmp);
                int size = (int) (length - current);
                current = length;
                String path = folderClient + "\\" + songName + "_EP" + ep + ".mp3";
                FileThreadDownload fThread = new FileThreadDownload(file, serverPath, path, current, size, tmp);
                fThread.start();
                System.out.println("Download 100% ! " + "Current " + current);
            }
        }
        
        
        boolean dk = true;
        while(dk){
            int dem=0;
            for(int i=0;i<arrF.size();i++){
                if(arrF.get(i).getValue()==true) dem++;
            }
            if(dem==ep) dk=false;
        }
        
        //Save
        System.out.println("Saving...");
        String savePath = folder + "\\" + songName + ".mp3";
        FileOutputStream fos = new FileOutputStream(new File(savePath));
        for (int i = 1; i <= ep; i++) {
            String filePath = folderClient + "\\" + songName + "_EP" + i + ".mp3";
            File filePart = new File(filePath);
            int size = (int) filePart.length();
            byte[] dataSave = new byte[size];
            FileInputStream fis = new FileInputStream(filePart);
            fis.read(dataSave, 0, size);
            fis.close();
            fos.write(dataSave);
        }
        fos.close();
        System.out.println("Saved in " + savePath);

       
    }
}
