/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rmichat.server;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class lanceServer implements Serializable{

    static String messageR;
    
    public void process(){
        try {
            // TODO add your handling code here:
        LocateRegistry.createRegistry(1099);
        ServeurChatImpl sc = new ServeurChatImpl();
            try {
                Naming.bind("Serveur", sc);
            } catch (AlreadyBoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        System.out.println("Demarrage du serveur...");
    }
        catch (RemoteException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void afficher(){
        System.out.println(messageR);
    }

    public static void main(String[] args)
    {
        lanceServer server= new lanceServer();
        server.process();
    }
}
