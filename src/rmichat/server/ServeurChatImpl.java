/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rmichat.server;

/**
 *
 * @author bach
 */
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmichat.client.ClientDistant;

/**
 *
 * @author bach
 */
public class ServeurChatImpl extends UnicastRemoteObject implements ServeurChat{
    ArrayList<ClientDistant> Clients = new ArrayList<ClientDistant>();
    ArrayList<String> Users=new ArrayList<String>();
    
    public ServeurChatImpl()throws RemoteException{
    }
    
    @Override
    public void Connect(ClientDistant s,String User)throws RemoteException{
        notifyClt(User);
        Clients.add(s);
        lanceServer.messageR=User+" Connecte ...";
        lanceServer.afficher();
        Users.add(User);
        //Server.list2.add(User);
        Notify(s);
    }

    //On doit passer tous les clients quand un nouvau client ce connect
    public void Notify(ClientDistant s){
        for(String Usr:Users)
            try {
            s.AddUser(Usr);
        } catch (RemoteException ex) {
            Logger.getLogger(ServeurChatImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Quand un client ce Deconnect
    @Override
    public void Desconnect(ClientDistant s,String Usr)throws RemoteException{
        Clients.remove(s);
        Users.remove(Usr);
        lanceServer.messageR=Usr+" Deconnect";
        lanceServer.afficher();
        //Server.list1.add(Usr+" Deconnect");
        //Server.list2.remove(Usr);
        notifyRoom(Usr);
    }

    //Invocke par les clients pour des nouveaux messages
    @Override
    public void Getmessage(String s,String Usr)throws RemoteException{
        lanceServer.messageR=Usr+" Say: "+s;
        lanceServer.afficher();
        notifyClient(s,Usr);
    }

    
    //Informer les clients quand un nouveau message arrive
    void notifyClient(String s,String Usr) throws RemoteException{
        for(ClientDistant x: Clients){
        try {
        x.Message(s,Usr);
        } catch (RemoteException e) { e.printStackTrace();}
        }
    }

    //Supprimer le client qui s'est Deconnecter
    void notifyRoom(String Usr){
        for(ClientDistant x: Clients){
            try {
                x.RemoveUser(Usr);
            } catch (RemoteException ex) {
                Logger.getLogger(ServeurChatImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Ajouter le client connecter dans les clients deja connecter
    void notifyClt(String Usr) throws RemoteException{
        for(ClientDistant clt:Clients){
            clt.AddUser(Usr);
        }
    }

    }   