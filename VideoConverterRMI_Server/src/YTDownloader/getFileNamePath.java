/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YTDownloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author Tuan Anh
 */
public class getFileNamePath {
    public static String getFileName(){
        String namePath = null;
        String file_path = "C:\\Users\\DucVu\\Documents\\NetBeansProjects\\VideoConverterRMI\\VideoConverterRMI_Server\\youtube_dl\\"; // nhap duong dan luu file youtubedl
        try {
            File filename_path = new File(file_path + "ten.txt");
            FileReader fr = new FileReader(filename_path);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null){
                namePath = line;
            }
            br.close();
            fr.close();
        } 
        catch (Exception e) {
            
        }
        return namePath;
    }
}
