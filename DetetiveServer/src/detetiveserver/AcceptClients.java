/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detetiveserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Gabe
 */
public class AcceptClients implements Runnable{
    private ServerSocket server;
    private ArrayList<Socket> clients;
    
    AcceptClients(ServerSocket server, ArrayList<Socket> clients){
        this.server = server;
        this.clients = clients;
    }
    
    @Override
    public void run(){
        //Atualmente só aceita um client
        while(true){
            try{
                clients.add(server.accept());
                //Notificar manage clients da mudança nos clients
            } catch (IOException e) {
                System.out.println("Accept failed: 4321");
                System.exit(-1);
            }
        }
    }
}
