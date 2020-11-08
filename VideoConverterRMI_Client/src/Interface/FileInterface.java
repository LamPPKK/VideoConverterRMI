/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import com.healthmarketscience.rmiio.RemoteInputStream;
import java.rmi.*;
import java.io.*;

/**
 *
 * @author Vu Minh Duc
 */
public interface FileInterface extends Remote {

    public void UploadFileToServer(RemoteInputStream ris,String serverPath, long length) throws RemoteException;

    public RemoteInputStream DownloadFileFromServer(String serverPath) throws RemoteException;
}
