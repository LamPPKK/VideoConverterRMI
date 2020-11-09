/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.rmi.*;
import java.io.*;

/**
 *
 * @author Vu Minh Duc
 */
public interface ConvertInterface extends Remote {

    public void ConvertFromFile(String source,String target) throws RemoteException;

    public byte[] ConvertFromYT(String link) throws RemoteException;
}
