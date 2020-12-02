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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
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
    public long ConvertFromFile(String source, String target) throws RemoteException {

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
        File fileT = new File(target);
        try {
            forMusic.encode(fileS, fileT, specifications);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Convert Done!");
        return fileT.length();
    }

    @Override
    public String ConvertFromYT(String url) throws RemoteException {
        String songName = null;
//        url = "https://www.youtube.com/watch?v=Zur-VMiDQ-w"; // nhap url video
        String download_path = "C:\\Users\\DucVu\\Documents\\NetBeansProjects\\VideoConverterRMI\\VideoConverterRMI_Server\\youtube_dl"; // nhap duong dan luu file youtubedl
        String doifile = " -o \"C:\\Users\\DucVu\\Documents\\NetBeansProjects\\VideoConverterRMI\\VideoConverterRMI_Server\\youtube_dl\\%(title)s.%(ext)s\" "; // nhap duong dan luu file youtubedl
        String toMp3 = " --extract-audio --audio-format mp3";
        String[] command
                = {
                    "cmd",};
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
            new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
            PrintWriter stdin = new PrintWriter(p.getOutputStream());
            stdin.println("cd " + download_path);
            stdin.println("youtube-dl " + "--simulate --get-title " + url + " > C:\\Users\\DucVu\\Documents\\NetBeansProjects\\VideoConverterRMI\\VideoConverterRMI_Server\\youtube_dl\\ten.txt");
            stdin.println("youtube-dl " + toMp3 + doifile + url);
            stdin.println("exit");
            stdin.close();
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String file_path = "C:\\Users\\DucVu\\Documents\\NetBeansProjects\\VideoConverterRMI\\VideoConverterRMI_Server\\youtube_dl\\"; // nhap duong dan luu file youtubedl
        try {
            File filename_path = new File(file_path + "ten.txt");
            FileReader fr = new FileReader(filename_path);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                songName = line;
            }
            br.close();
            fr.close();
        } catch (Exception e) {

        }

        if (songName == null) {
            System.out.println("nope");
        } else {
            System.out.println("Song name: " + songName);
        }
//        DeleteFile.action(); // ham xoa cac file sau khi tai
        String serverPath = download_path + "\\" + songName + ".mp3";
        return serverPath;
    }

}
