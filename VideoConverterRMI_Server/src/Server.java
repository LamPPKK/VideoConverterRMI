/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

    public Server() throws RemoteException {

    }

    public static void main(String[] args) throws RemoteException, IOException {
        ServerSocket server = new ServerSocket(3004);
        try {
            Registry reg = LocateRegistry.createRegistry(9999);
            reg.rebind("convert from file", new Server());
            Registry reg2 = LocateRegistry.createRegistry(8888);
            reg2.rebind("convert from link youtube", new Server());
        } catch (Exception e) {
            System.out.println("Exception " + e);
        }
        while (true) {
            connection = server.accept();
            System.out.println("1 devices has connected!");
        }
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
            
            System.out.println(source.getAbsolutePath().toString());

//            String folder = "C:\\Users\\Vu Minh Duc\\Documents\\NetBeansProjects\\LapTrinhMang\\VideoConverterServer\\Music";
//            String fileName = source.getName();
//            System.out.println(fileName.substring(0, fileName.lastIndexOf(".")).toString());
//            File target = new File(folder + "\\" + fileName.substring(0, fileName.lastIndexOf(".")) + ".mp3");
//            System.out.println(folder + "\\" + fileName.substring(0, fileName.lastIndexOf(".")) + ".mp3");
//            System.out.println(target.getAbsolutePath());
//            forMusic.encode(source, target, specifications);
//            System.out.println("Done!");
//            byte[] content = Files.readAllBytes(Paths.get(target.getPath()));
//            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
//            dos.writeInt(content.length);
//            dos.write(content);
//            System.out.println("Sent!");
//            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ConvertFromYT() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
