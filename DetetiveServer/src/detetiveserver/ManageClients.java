/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detetiveserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabe
 */
public class ManageClients extends Thread{
    private final ServerSocket server;
    private ArrayList<Socket> clients;
    private final PipedOutputStream pos;
    private final PipedInputStream pis;
   
    ManageClients(ServerSocket server, ArrayList<Socket> clients, PipedOutputStream pos, PipedInputStream pis){
        this.server = server;
        this.clients = clients;
        this.pos = pos;
        this.pis = pis;

    }
    
    @Override
    public void run(){
        ObjectInputStream pipeois = null;
        for(Socket client : clients){
            try {
                ObjectInputStream clientois = new ObjectInputStream(client.getInputStream());
                if(clientois.readObject().equals("Start")){
                    informClients();
                    informGameStart();
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(ManageClients.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ArrayList<Socket> newClients = new ArrayList();
        try{
            pipeois = new ObjectInputStream(pis);
        } catch (IOException e) {
            System.out.println("Could not create object input stream");
            System.exit(-1);
        }
        while(true){
            try {
                newClients = (ArrayList<Socket>) pipeois.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(ManageClients.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(!(newClients.equals(clients))){
                this.clients = newClients;
                try {
                    informClients();
                } catch (IOException ex) {
                    Logger.getLogger(ManageClients.class.getName()).log(Level.SEVERE, null, ex);
                }
            }            
        }
    }
    
    public void informClients() throws IOException{
        for(Socket client : clients){
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            out.writeObject(clients);
        }
    }
    
    public void informGameStart() throws IOException{
        for(Socket client : clients){
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            out.writeObject("Game Start");
        }
    }
}
