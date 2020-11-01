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

    private Socket clientSocket;

    public Client() throws IOException {
        clientSocket = new Socket("localhost", 3004);
//        clientSocket.setSoTimeout(20000);
    }

    public static void main(String[] args) throws IOException {
        new MainView(new Client()).run();
    }

    public void ConvertFromFile(File source, File target) throws IOException {
        DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
        try {
            Registry reg = LocateRegistry.getRegistry("localhost", 9999);
            Convert convert = (Convert) reg.lookup("convert from file");
            System.out.println("Ask request");
            convert.ConvertFromFile(source);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        int length = dis.readInt();
        System.out.println("Length received! :"+length);
        byte[] receive = new byte[length];
        dis.readFully(receive, 0, receive.length);
        System.out.println("Content received!");
*/
        String nameFile = source.getName();
        String savePath = target.getAbsolutePath() + "\\\\" + nameFile.substring(0, nameFile.lastIndexOf(".")) + ".mp3";
//        System.out.println(savePath);
//        FileOutputStream fos = new FileOutputStream(savePath);
//        fos.write(receive);

        byte[] contents = new byte[10000];

        //Initialize the FileOutputStream to the output file's full path.
        FileOutputStream fos = new FileOutputStream(savePath);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        InputStream is = clientSocket.getInputStream();

        //No of bytes read in one read() call
        int bytesRead = 0;
//        System.out.println(is.markSupported());
        while ((bytesRead = is.read(contents)) != -1) {
            bos.write(contents, 0, bytesRead);
//            bos.flush();
//            System.out.println("Receiving...  ");
        }
        bos.flush();
        clientSocket.close();

        System.out.println("File saved successfully!");
//        clientSocket.close();
    }
}
