/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detetiveserver;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Gabe
 */
public class AcceptClients extends Thread{
    private final ServerSocket server;
    private ArrayList<Socket> clients;
    private final PipedOutputStream pos;
    private final PipedInputStream pis;
    
    AcceptClients(ServerSocket server, ArrayList<Socket> clients, PipedOutputStream pos, PipedInputStream pis){
        this.server = server;
        this.clients = clients;
        this.pos = pos;
        this.pis = pis;
    }
    
    @Override
    public void run(){
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(pos);
        } catch (IOException ex) {
            System.out.println("Could not create object output stream");
        }
        while(true){
            try{
                clients.add(server.accept());
                oos.writeObject(clients);
            } catch (IOException e) {
                System.out.println("Accept failed: 4321");
                System.exit(-1);
            }
        }
    }
}
