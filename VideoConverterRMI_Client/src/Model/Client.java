/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import View.MainView;
import java.io.*;
import java.util.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.net.*;

/**
 *
 * @author Vu Minh Duc
 */
public class Client {

    private static Socket clientSocket;

    public static void main(String[] args) throws IOException {
        clientSocket = new Socket("localhost", 3004);
        new MainView(new Client()).run();
    }

    public void ConvertFromFile(File source, File target) throws IOException {
        try {
            Registry reg = LocateRegistry.getRegistry("localhost", 9999);
            Convert convert = (Convert) reg.lookup("convert from file");
            convert.ConvertFromFile(source);

            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            int length = dis.readInt();
            byte[] receive = new byte[length];
            dis.readFully(receive, 0, receive.length);
            String nameFile=source.getName();
            String savePath = target.getAbsolutePath() + "\\" + nameFile.substring(0,nameFile.lastIndexOf("."))+ ".mp3";
            System.out.println(savePath);
            FileOutputStream fos = new FileOutputStream(savePath);
            fos.write(receive);
        } catch (Exception e) {
            System.out.println("Exception " + e);
        }
        clientSocket.close();
    }
}
