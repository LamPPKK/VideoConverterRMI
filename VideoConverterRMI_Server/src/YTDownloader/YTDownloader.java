/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YTDownloader;

import java.io.File;
import java.io.IOException;

public class YTDownloader {
    public static void main(String[] args)  throws IOException {
        String namefile = null;
        String url = "https://www.youtube.com/watch?v=lNCViM7R8N8" ; // nhap url video
        DownAndGetName.Action(url);
        namefile = getFileNamePath.getFileName();
        
        if(namefile == null){
            System.out.println("nope");
        }
        else System.out.println("Song name: " +namefile);
//        DeleteFile.action(); // ham xoa cac file sau khi tai
    }
}	