/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rmichat.client;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmichat.server.ServeurChat;

/**
 *
 * @author user
 */
public class lanceClient implements Serializable{

    ServeurChat cd;
    ClientDistantImpl cdi;
    String msg;
    String pseudo;
    static String message;
    public void connecter(){
        try {
            try {
                cd =(ServeurChat) Naming.lookup("Serveur");
            } catch (NotBoundException ex) {
                Logger.getLogger(lanceClient.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                Logger.getLogger(lanceClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            cdi = new ClientDistantImpl();
            System.out.println("user?");
            Scanner sc= new Scanner(System.in);
            pseudo=sc.nextLine();
            cd.Connect(cdi,pseudo);
            while(true){
                System.out.println("ecrire msg:");
                envoiMsg(sc.nextLine());
            }
        } catch (RemoteException ex) {
            Logger.getLogger(lanceClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void envoiMsg(String s){
         msg=s;
        try {
            cd.Getmessage(msg,pseudo);
        } catch (RemoteException ex) {
            Logger.getLogger(lanceClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void afficher(){
        System.out.println(message);
    }

    public static void main(String[] args)
    {
        lanceClient cl= new lanceClient();
        cl.connecter();
    }
}
