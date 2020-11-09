/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Interface.ConvertInterface;
import Interface.FileInterface;
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

    public void startConvert(FileInterface file, ConvertInterface convert, File source, File target) throws RemoteException, FileNotFoundException, IOException {
        int length = (int) source.length();
//        System.out.println("File length: "+length);
        byte[] dataUpload = new byte[length];
        FileInputStream fis = new FileInputStream(source);
        fis.read(dataUpload, 0, length);
        fis.close();
        String fileName = source.getName();
//        System.out.println("File name : "+fileName);
        String serverPathMp4 = "G:\\Java\\VideoConverterRMI-main\\VideoConverterRMI_Server\\Music" + "\\" + fileName.substring(0, fileName.lastIndexOf(".")) + ".mp4";
        String serverPathMp3 = "G:\\Java\\VideoConverterRMI-main\\VideoConverterRMI_Server\\Music" + "\\" + fileName.substring(0, fileName.lastIndexOf(".")) + ".mp3";

//        System.out.println("Server path: " + serverPath);
        //Upload File to server
        System.out.println("Uploading...");
        file.UploadFileToServer(dataUpload, serverPathMp4);
        //Convert
        System.out.println("Converting...");
        convert.ConvertFromFile(serverPathMp4, serverPathMp3);
        //Download File from server
        System.out.println("Downloading...");
        byte[] data = file.DownloadFileFromServer(serverPathMp3);
        //Save
        System.out.println("Saving...");
        String savePath = target + "\\" + fileName.substring(0, fileName.lastIndexOf(".")) + ".mp3";
        FileOutputStream fos = new FileOutputStream(new File(savePath));
        fos.write(data, 0, data.length);
        fos.flush();
        fos.close();
    }
}
