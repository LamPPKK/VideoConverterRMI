/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Interface.ConvertInterface;
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
    public long ConvertFromFile(String source,String target) throws RemoteException{

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
        File fileS = new File(source);
        File fileT=new File(target);
        try {
            forMusic.encode(fileS,fileT, specifications);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Convert Done!");
        return fileT.length();
    }

    @Override
    public byte[] ConvertFromYT(String link) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
