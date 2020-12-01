/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YTDownloader;

import java.io.PrintWriter;

/**
 *
 * @author Tuan Anh
 */
public class DownAndGetName {
    public static void Action(String url){
        String download_path="C:\\Users\\DucVu\\Documents\\NetBeansProjects\\VideoConverterRMI\\VideoConverterRMI_Server\\youtube_dl"; // nhap duong dan luu file youtubedl
        String doifile = "-o \"C:\\Users\\DucVu\\Documents\\NetBeansProjects\\VideoConverterRMI\\VideoConverterRMI_Server\\youtube_dl\\%(title)s.%(ext)s\" "; // nhap duong dan luu file youtubedl
//        String toMp3 = " --extract-audio --audio-format mp3";
        String[] command =  
	    {
	        "cmd",
	    };
	Process p;
	try {   
            p = Runtime.getRuntime().exec(command);
            new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
            new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
            PrintWriter stdin = new PrintWriter(p.getOutputStream());            
            stdin.println("cd " + download_path);
            stdin.println("youtube-dl " +"--simulate --get-title "+ url +" > C:\\Users\\DucVu\\Documents\\NetBeansProjects\\VideoConverterRMI\\VideoConverterRMI_Server\\youtube_dl\\ten.txt");
            stdin.println("youtube-dl " + doifile + url);
            stdin.println("exit");
            stdin.close();
            p.waitFor();
    	} catch (Exception e) {
            e.printStackTrace();
	}
    }
}
