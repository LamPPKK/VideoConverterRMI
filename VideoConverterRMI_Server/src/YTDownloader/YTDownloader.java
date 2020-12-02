/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YTDownloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class YTDownloader {

    public String downloadFromYT(String url) throws IOException {
        String songName = null;
//        url = "https://www.youtube.com/watch?v=Zur-VMiDQ-w"; // nhap url video
        String download_path = "C:\\Users\\DucVu\\Documents\\NetBeansProjects\\VideoConverterRMI\\VideoConverterRMI_Server\\youtube_dl"; // nhap duong dan luu file youtubedl
        String doifile = "-o \"C:\\Users\\DucVu\\Documents\\NetBeansProjects\\VideoConverterRMI\\VideoConverterRMI_Server\\youtube_dl\\%(title)s.%(ext)s\" "; // nhap duong dan luu file youtubedl
//        String toMp3 = " --extract-audio --audio-format mp3";
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
            stdin.println("youtube-dl " + doifile + url);
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
        String serverPath=download_path + "\\" +songName + ".mp4";
        return serverPath;
    }
}
