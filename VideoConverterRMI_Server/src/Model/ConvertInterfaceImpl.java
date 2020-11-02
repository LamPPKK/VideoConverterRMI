/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.rmi.*;
import java.rmi.server.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vu Minh Duc
 */
public class ConvertInterfaceImpl extends UnicastRemoteObject implements ConvertInterface {

    public ConvertInterfaceImpl() throws RemoteException {

    }

    @Override
    public String ConvertFromFile(String filePath) throws RemoteException{

        Encoder forMusic = new Encoder();
        //audioAttribute obj
        AudioAttributes audio = new AudioAttributes();
        audio.setVolume(256);//default
        audio.setCodec("libmp3lame");
        audio.setBitRate(128000);
        audio.setChannels(2);
        audio.setSamplingRate(44100);

        EncodingAttributes specifications = new EncodingAttributes();
        specifications.setFormat("mp3");
        specifications.setAudioAttributes(audio);
        File source = new File(filePath);
        String fileName = source.getName();
//            System.out.println(source.getAbsolutePath().toString());
        String folder = "C:\\Users\\Vu Minh Duc\\Documents\\NetBeansProjects\\VideoConverterRMI\\VideoConverterRMI_Server\\Music";
        System.out.println("File name: " + fileName.substring(0, fileName.lastIndexOf(".")).toString());
        File target = new File(folder + "\\" + fileName.substring(0, fileName.lastIndexOf(".")) + ".mp3");
        try {
//            System.out.println(folder + "\\" + fileName.substring(0, fileName.lastIndexOf(".")) + ".mp3");
//            System.out.println(target.getAbsolutePath());
            forMusic.encode(source, target, specifications);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Convert Done!");
        String path=target.getAbsolutePath();
        return path;
    }

    @Override
    public byte[] ConvertFromYT(String link) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
