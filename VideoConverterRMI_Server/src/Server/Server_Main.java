/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/**
 *
 * @author DucVu
 */
public class Server_Main {
    public static void main(String[] args) {
        try{
            new Server().run();
        }catch(Exception e){
            System.out.println("Exception "+e);
            e.printStackTrace();
        }
    }
}
