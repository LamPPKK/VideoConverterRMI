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
        Client c=new Client();
        new MainView(c).run();
    }
 
}
