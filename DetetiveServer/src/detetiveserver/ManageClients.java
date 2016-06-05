/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detetiveserver;

import detetiveutils.Jogador;
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
    private ArrayList<Jogador> clients;
    private final PipedOutputStream pos;
    private final PipedInputStream pis;
   
    ManageClients(ServerSocket server, ArrayList<Jogador> clients, PipedOutputStream pos, PipedInputStream pis){
        this.server = server;
        this.clients = clients;
        this.pos = pos;
        this.pis = pis;

    }
    
    @Override
    public void run(){
        ObjectInputStream pipeois = null;
        for(Jogador client : clients){
            try {
                ObjectInputStream clientois = new ObjectInputStream(client.getSocket().getInputStream());
                if(clientois.readObject().equals("Game Start")){
                    informClients();
                    informGameStart();
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(ManageClients.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ArrayList<Jogador> newClients = new ArrayList();
        try{
            pipeois = new ObjectInputStream(pis);
        } catch (IOException e) {
            System.out.println("Could not create object input stream");
            System.exit(-1);
        }
        while(true){
            try {
                newClients = (ArrayList<Jogador>) pipeois.readObject();
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
        for(Jogador client : clients){
            ObjectOutputStream out = new ObjectOutputStream(client.getSocket().getOutputStream());
            out.writeObject(clients);
        }
    }
    
    public void informGameStart() throws IOException{
        for(Jogador client : clients){
            ObjectOutputStream out = new ObjectOutputStream(client.getSocket().getOutputStream());
            out.writeObject("Game Start");
        }
    }
}
