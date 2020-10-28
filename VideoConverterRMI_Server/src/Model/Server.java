/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import java.io.*;
import java.net.ServerSocket;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vu Minh Duc
 */
public class Server extends UnicastRemoteObject implements Convert {

    private static ServerSocket server;
    private static Socket connection;

    public Server() throws RemoteException, IOException {
        this.server = new ServerSocket(3004);
        try {
            Registry reg = LocateRegistry.createRegistry(9999);
            reg.rebind("convert from file",this);
            Registry reg2 = LocateRegistry.createRegistry(8888);
            reg2.rebind("convert from link youtube",this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (true) {
            this.connection = server.accept();
            System.out.println("1 devices has connected!");
        }
    }

    public static void main(String[] args) throws RemoteException, IOException {
        new Server();
    }

    @Override
    public void ConvertFromFile(File source) throws RemoteException {

        try {
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

//            System.out.println(source.getAbsolutePath().toString());
            String folder = "C:\\Users\\Vu Minh Duc\\Documents\\NetBeansProjects\\VideoConverterRMI\\VideoConverterRMI_Server\\Music";
            String fileName = source.getName();
//            System.out.println(fileName.substring(0, fileName.lastIndexOf(".")).toString());
            File target = new File(folder + "\\" + fileName.substring(0, fileName.lastIndexOf(".")) + ".mp3");
            System.out.println(folder + "\\" + fileName.substring(0, fileName.lastIndexOf(".")) + ".mp3");
//            System.out.println(target.getAbsolutePath());
            forMusic.encode(source, target, specifications);
            System.out.println("Done!");

            byte[] content = Files.readAllBytes(Paths.get(target.getPath()));
            System.out.println(content.length);
            DataOutputStream dos = new DataOutputStream(this.connection.getOutputStream());
            dos.writeInt(content.length);
            System.out.println("Length sent!");
            dos.flush();
            dos.write(content);
            System.out.println("Content sent!");
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ConvertFromYT() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
