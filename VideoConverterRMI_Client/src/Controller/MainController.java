/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
/**
 *
 * @author Vu Minh Duc
 */
public class MainController {
    public String getLink(JTextField in){
        String link = in.getText();
        return link;
    }
    public File GetSelectedFile(JFileChooser choose){
        File folder = choose.getSelectedFile();
        return folder;
    }
    public void ShowSelectedFile(JFileChooser choose,JTextField out){
        out.setText(choose.getSelectedFile().getPath());
    }
    public void startConvert(File source,File target){
        
    }
}
